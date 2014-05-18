import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 
 * @author Vinckel
 * @version 20140519
 *
 */
public class DocumentParser {
	private static final String TABLE_NAME = "mid"; // 게시판 이름
	private static final long SLEEP_INTERVAL = 500L;  // 파싱 딜레이
	private static final String FILE_DIR = System.getenv("GOOGLE_DRIVE") + "\\취미\\OUDocs\\" + TABLE_NAME + "\\";
	private static final Pattern txtPattern = Pattern.compile("^[0-9]+");
	
	public static void main(String[] args) {
		int startNum = 0;
		int endNum = 6795;

		List<OUDoc> ouDocList = new ArrayList<OUDoc>();
		File ouDocs[] = new File(FILE_DIR).listFiles();
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			for (int i = 0; i < ouDocs.length; i++) {
				Matcher m = txtPattern.matcher(ouDocs[i].getName());
				while (m.find()) {
					int tmpNum = Integer.parseInt(m.group(0));
					if (startNum < tmpNum) {
						startNum = tmpNum;
					}
				}
				File readFile = new File(FILE_DIR + ouDocs[i].getName());
				char[] c = new char[(int) readFile.length()];
				br = new BufferedReader(new InputStreamReader(new FileInputStream(readFile), "UTF8"));
				br.read(c);
				
				OUDoc tmpOUDoc = new OUDoc();
				Document doc = Jsoup.parse(String.valueOf(c));
				tmpOUDoc.setRawHtml(doc.toString());
				tmpOUDoc.setTitle(doc.select(".view_subject").text());
				tmpOUDoc.setContent(doc.select(".view_content").text());
				ouDocList.add(tmpOUDoc);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("docID " + (++startNum) + "부터 검색을 시작합니다.");
		
		try {
			for (int docNum = startNum; docNum <= endNum; docNum++) {
				File writeFile = new File(FILE_DIR + docNum + ".txt");
				OUDoc tmpOUDoc = new OUDoc();
				Document doc = Jsoup.connect("http://todayhumor.com/?" + TABLE_NAME + "_" + docNum).get();
				String title = doc.select(".view_subject").text();
				
				if (title == null || title.length() == 0) {
					continue;
				} else {
					bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeFile), "UTF8"));
					tmpOUDoc.setRawHtml(doc.toString());
					tmpOUDoc.setTitle(title);
					tmpOUDoc.setContent(doc.select(".view_content").text());
					
					bw.write(tmpOUDoc.getRawHtml());
				}
				
				Thread.sleep(SLEEP_INTERVAL);
//				System.out.println(tmpOUDoc.getRawHtml());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
