import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 
 * @author Vinckel
 * @version 20140518
 *
 */
public class DocumentParser {
	private static final String FILE_DIR = System.getenv("GOOGLE_DRIVE") + "\\취미\\OUDocs\\";
	private static final Pattern txtPattern = Pattern.compile("^[0-9]+");
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		int startNum = 0;
		int endNum = 7730;

		List<OUDoc> ouDocList = new ArrayList<OUDoc>();
		File ouDocs[] = new File(FILE_DIR).listFiles();
		
		try {
			for (int i = 0; i < ouDocs.length; i++) {
				Matcher m = txtPattern.matcher(ouDocs[i].getName());
				while (m.find()) {
					int tmpNum = Integer.parseInt(m.group(0));
					if (startNum < tmpNum) {
						startNum = tmpNum;
					}
				}
				File f = new File(FILE_DIR + ouDocs[i].getName());
				char[] c = new char[(int) f.length()];
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"UTF8"));
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
		
//		try {
//			for (int docNum = startNum; docNum <= endNum; docNum++) {
//				StringBuilder fileLoc = new StringBuilder(FILE_DIR).append("").append(docNum);
//				
//				File file = new File(fileLoc.toString());
//				OUDoc tmpOUDoc = new OUDoc();
//				Document doc = Jsoup.connect("http://todayhumor.com/?mid_" + docNum).get();
//				tmpOUDoc.setRawHtml(doc.toString());
//				tmpOUDoc.setTitle(doc.select(".view_subject").text());
//				tmpOUDoc.setContent(doc.select(".view_content").text());
//	
//				System.out.println(tmpOUDoc.getRawHtml());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
