import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 
 * @author Vinckel
 * @version 20140520
 *
 */
public class DocumentParser {
	private static final String TABLE_NAME = "mid"; // 게시판 이름
	private static final String CATEGORY_NAME = "왕좌의 게임";
	private static final long SLEEP_INTERVAL = 500L;  // 파싱 딜레이
	private static final String FILE_DIR = System.getenv("GOOGLE_DRIVE") + "\\취미\\OUDocs\\" + TABLE_NAME + "\\";
	private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]+");
	private static final String MID_NAME_REGEXP = ".*(왕좌|티리온|조프리|winter is coming|왕겜|얼불노).*";
	
	private static final String tyrion = ".*(티리온|난쟁이|난장이).*";
	private static final String cersei = ".*(세르세이|서세이).*";
	private static final String jaime = ".*(제이미|자이메).*";
	private static final String sansa = ".*(산사).*";
	private static final String hodor = ".*(호도).*";
	private static final String shae = ".*(샤에|셰이).*";
	private static final String varys = ".*(바리스|대머리 고자아저씨).*";
	private static final String tywin = ".*(티윈|타이윈).*";
	private static final String daenerys = ".*(대너리스|드네리스|데너리스|드내리스|대니).*";
	private static final String john = ".*(존스노우|존 스노우|존눈).*";
	private static final String hound = ".*(하운드|산도르).*";
	private static final String arya = ".*(아리아|아리야).*";
	private static final String joffrey = ".*(조프리|조뿌리|좆뿌리).*";
	private static final String ygritte = ".*(이그리트).*";
	private static final String margaery = ".*(마저리).*";
	private static final String littlefinger = ".*(피터|리틀핑거|베일리쉬|바일리쉬).*";
	private static final String oberyn = ".*(오베린|오베른).*";
	
	private static final String baratheon = ".*(바라테온).*";
	private static final String stark = ".*(스타크).*";
	private static final String lannister = ".*(라니스터).*";
	private static final String targaryen = ".*(타르가르옌|타가리엔|타가리옌).*";
	private static final String tyrell = ".*(타이렐|티렐).*";
	

	private static void init(Map<String, Integer> calledCharacters) {
		calledCharacters.put("tyrion", 0);
		calledCharacters.put("cersei", 0);
		calledCharacters.put("jaime", 0);
		calledCharacters.put("sansa", 0);
		calledCharacters.put("hodor", 0);
		calledCharacters.put("shae", 0);
		calledCharacters.put("varys", 0);
		calledCharacters.put("tywin", 0);
		calledCharacters.put("daenerys", 0);
		calledCharacters.put("john", 0);
		calledCharacters.put("hound", 0);
		calledCharacters.put("arya", 0);
		calledCharacters.put("joffrey", 0);
		calledCharacters.put("margaery", 0);
		calledCharacters.put("littlefinger", 0);
		
		calledCharacters.put("baratheon", 0);
		calledCharacters.put("stark", 0);
		calledCharacters.put("lannister", 0);
		calledCharacters.put("targaryen", 0);
		calledCharacters.put("tyrell", 0);
	}
	
	public static void main(String[] args) {
		int startNum = 0;
		int endNum = 6820;
		Map<String, Object> normalStats = new HashMap<String, Object>();
		Map<String, Object> specialStats = new HashMap<String, Object>();
		Map<String, Integer> calledCharacters = new HashMap<String, Integer>();
		Map<String, Map<String, Integer>> peopleStats = new HashMap<String, Map<String, Integer>>();
		init(calledCharacters);
		List<OUDoc> normalDocs = new ArrayList<OUDoc>();
		List<OUDoc> specialDocs = new ArrayList<OUDoc>();
		File ouFiles[] = new File(FILE_DIR).listFiles();
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			for (int i = 0; i < ouFiles.length; i++) {
				Matcher m = NUMBER_PATTERN.matcher(ouFiles[i].getName());
				if (m.find()) {
					int tmpNum = Integer.parseInt(m.group(0));
					if (startNum < tmpNum) {
						startNum = tmpNum;
					}
				} else {
					continue;
				}
				File readFile = new File(FILE_DIR + ouFiles[i].getName());
				char[] c = new char[(int) readFile.length()];
				br = new BufferedReader(new InputStreamReader(new FileInputStream(readFile), "UTF8"));
				br.read(c);
				Document doc = Jsoup.parse(String.valueOf(c));
				putToArray(doc, calledCharacters, specialDocs, normalDocs);
//				OUDoc tmpOUDoc = new OUDoc(doc.toString());
//				String allTexts = tmpOUDoc.getTitle() + " " + tmpOUDoc.getContent();
//				if (Pattern.matches(MID_NAME_REGEXP, allTexts)) {
//					extractDataWithRegExp(calledCharacters, allTexts);
//					specialDocs.add(tmpOUDoc);
//				} else {
//					normalDocs.add(tmpOUDoc);
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("docID " + (++startNum) + "부터 검색을 시작합니다.");
//		try {
//			for (int docNum = startNum; docNum <= endNum; docNum++) {
//				File writeFile = new File(FILE_DIR + docNum + ".html");
//				Document doc = Jsoup.connect("http://todayhumor.com/?" + TABLE_NAME + "_" + docNum).get();
//				String title = doc.select(".view_subject").text();
//				
//				if (title == null || title.length() == 0) {
//					continue;
//				} else {
//					bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeFile), "UTF8"));
//					OUDoc tmpOUDoc = new OUDoc(doc.toString());
//					String allTexts = tmpOUDoc.getTitle() + " " + tmpOUDoc.getContent();
//					if (Pattern.matches(MID_NAME_REGEXP, allTexts)) {
//						extractDataWithRegExp(calledCharacters, allTexts);
//						specialDocs.add(tmpOUDoc);
//					} else {
//						normalDocs.add(tmpOUDoc);
//					}
//					
//					bw.write(tmpOUDoc.getRawHtml());
//					bw.flush();
//				}
//				
//				Thread.sleep(SLEEP_INTERVAL);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		summary(normalStats, specialStats, normalDocs, specialDocs, peopleStats);
		
		try {
			File writeFile = new File(FILE_DIR + "statistics_docs.txt");
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeFile), "UTF8"));
			bw.write("분류\t비율(글 개수)\t평균 조회수\t평균 댓글수\t평균 추천\t평균 반대\n");
			bw.write("일반\t" + 
					(double) normalDocs.size() / (normalDocs.size() + specialDocs.size()) + "(" + normalDocs.size() + ")\t" +
					normalStats.get("avgReadCount") + "\t" + normalStats.get("avgReplyCount") + "\t" + normalStats.get("avgGoodCount") + "\t" + normalStats.get("avgBadCount")
					+ "\n");
			bw.write(CATEGORY_NAME + "\t" + 
					(double) specialDocs.size() / (normalDocs.size() + specialDocs.size()) + "(" + specialDocs.size() + ")\t" +
					specialStats.get("avgReadCount") + "\t" + specialStats.get("avgReplyCount") + "\t" + specialStats.get("avgGoodCount") + "\t" + specialStats.get("avgBadCount")
					+ "\n");
			bw.flush();
			
			writeFile = new File(FILE_DIR + "statistics_show.txt");
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeFile), "UTF8"));
			bw.write("이름\t불린 횟수\n");
			for (String key : calledCharacters.keySet()) {
				bw.write(key + "\t" + calledCharacters.get(key) + "\n");
			}
			
			bw.flush();
			
			writeFile = new File(FILE_DIR + "statistics_people.txt");
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeFile), "UTF8"));
			bw.write("닉네임\t글 쓴 횟수\t답변 수\t추천 수\n");
			for (String key : peopleStats.keySet()) {
				bw.write(key + "\t" + peopleStats.get(key).get("docCount") + "\t" + peopleStats.get(key).get("replyCount") + "\t" + peopleStats.get(key).get("goodCount") + "\n");
			}
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void summary(Map<String, Object> normalStats, Map<String, Object> specialStats, List<OUDoc> normalDocs, List<OUDoc> specialDocs, Map<String, Map<String, Integer>> peopleStats) {
		int readCount = 0;
		int goodCount = 0;
		int badCount = 0;
		int replyCount = 0;
		
		for (OUDoc ouDoc : normalDocs) {
			readCount += ouDoc.getReadCount();
			goodCount += ouDoc.getGoodCount();
			badCount += ouDoc.getBadCount();
			replyCount += ouDoc.getReplyCount();
		}
		
		normalStats.put("docs", normalDocs);
		normalStats.put("docsSize", normalDocs.size());
		normalStats.put("avgReadCount", (double) readCount / normalDocs.size());
		normalStats.put("avgGoodCount", (double)  goodCount / normalDocs.size());
		normalStats.put("avgBadCount", (double) badCount / normalDocs.size());
		normalStats.put("avgReplyCount", (double) replyCount / normalDocs.size());
		
		readCount = 0;
		goodCount = 0;
		badCount = 0;
		replyCount = 0;
		
		for (OUDoc ouDoc : specialDocs) {
			readCount += ouDoc.getReadCount();
			goodCount += ouDoc.getGoodCount();
			badCount += ouDoc.getBadCount();
			replyCount += ouDoc.getReplyCount();
			
			if (peopleStats.get(ouDoc.getWriter()) == null) {
				ouDoc.getWriter();
				Map<String, Integer> stat = new HashMap<String, Integer>();
				stat.put("docCount", 1);
				stat.put("replyCount", 0);
				stat.put("goodCount", 0);
				peopleStats.put(ouDoc.getWriter(), stat);
				
				for (String goodPerson : ouDoc.getGoodPeople()) {
					if (peopleStats.get(goodPerson) == null) {
						stat = new HashMap<String, Integer>();
						stat.put("docCount", 0);
						stat.put("replyCount", 0);
						stat.put("goodCount", 1);
						peopleStats.put(goodPerson, stat);
					} else {
						peopleStats.get(goodPerson).put("goodCount", peopleStats.get(goodPerson).get("goodCount") + 1);
					}
				}
				
				for (String replyPerson : ouDoc.getReplyPeople()) {
					if (peopleStats.get(replyPerson) == null) {
						stat = new HashMap<String, Integer>();
						stat.put("docCount", 0);
						stat.put("replyCount", 1);
						stat.put("goodCount", 0);
						peopleStats.put(replyPerson, stat);
					} else {
						peopleStats.get(replyPerson).put("replyCount", peopleStats.get(replyPerson).get("replyCount") + 1);
					}
				}
			} else {
				peopleStats.get(ouDoc.getWriter()).put("docCount", peopleStats.get(ouDoc.getWriter()).get("docCount") + 1);
			}
		}
		specialStats.put("docs", specialDocs);
		specialStats.put("docsSize", (double) specialDocs.size() / specialDocs.size());
		specialStats.put("avgReadCount", (double) readCount / specialDocs.size());
		specialStats.put("avgGoodCount", (double) goodCount / specialDocs.size());
		specialStats.put("avgBadCount", (double) badCount / specialDocs.size());
		specialStats.put("avgReplyCount", (double) replyCount / specialDocs.size());
	}
	private static void putToArray(Document doc, Map<String, Integer> calledCharacters, List<OUDoc> specialDocs, List<OUDoc> normalDocs) {
		OUDoc tmpOUDoc = new OUDoc(doc.toString());
		String allTexts = tmpOUDoc.getTitle() + " " + tmpOUDoc.getContent();
		if (Pattern.matches(MID_NAME_REGEXP, allTexts)) {
			extractDataWithRegExp(calledCharacters, allTexts);
			specialDocs.add(tmpOUDoc);
		} else {
			normalDocs.add(tmpOUDoc);
		}
	}
	private static void extractDataWithRegExp(Map<String, Integer> calledCharacters, String allTexts) {
		if (Pattern.matches(tyrion, allTexts)) {
			calledCharacters.put("tyrion", calledCharacters.get("tyrion") + 1);

		}
		if (Pattern.matches(cersei, allTexts)) {
			calledCharacters.put("cersei", calledCharacters.get("cersei") + 1);

		}
		if (Pattern.matches(jaime, allTexts)) {
			calledCharacters.put("jaime", calledCharacters.get("jaime") + 1);

		}
		if (Pattern.matches(sansa, allTexts)) {
			calledCharacters.put("sansa", calledCharacters.get("sansa") + 1);

		}
		if (Pattern.matches(hodor, allTexts)) {
			calledCharacters.put("hodor", calledCharacters.get("hodor") + 1);

		}
		if (Pattern.matches(shae, allTexts)) {
			calledCharacters.put("shae", calledCharacters.get("shae") + 1);

		}
		if (Pattern.matches(varys, allTexts)) {
			calledCharacters.put("varys", calledCharacters.get("varys") + 1);

		}
		if (Pattern.matches(tywin, allTexts)) {
			calledCharacters.put("tywin", calledCharacters.get("tywin") + 1);

		}
		if (Pattern.matches(daenerys, allTexts)) {
			calledCharacters.put("daenerys", calledCharacters.get("daenerys") + 1);

		}
		if (Pattern.matches(john, allTexts)) {
			calledCharacters.put("john", calledCharacters.get("john") + 1);

		}
		if (Pattern.matches(hound, allTexts)) {
			calledCharacters.put("hound", calledCharacters.get("hound") + 1);

		}
		if (Pattern.matches(arya, allTexts)) {
			calledCharacters.put("arya", calledCharacters.get("arya") + 1);

		}
		if (Pattern.matches(joffrey, allTexts)) {
			calledCharacters.put("joffrey", calledCharacters.get("joffrey") + 1);

		}
		if (Pattern.matches(margaery, allTexts)) {
			calledCharacters.put("margaery", calledCharacters.get("margaery") + 1);

		}
		if (Pattern.matches(littlefinger, allTexts)) {
			calledCharacters.put("littlefinger", calledCharacters.get("littlefinger") + 1);

		}
		if (Pattern.matches(oberyn, allTexts)) {
			calledCharacters.put("oberyn", calledCharacters.get("oberyn") + 1);

		}
		if (Pattern.matches(baratheon, allTexts)) {
			calledCharacters.put("baratheon", calledCharacters.get("baratheon") + 1);

		}
		if (Pattern.matches(stark, allTexts)) {
			calledCharacters.put("stark", calledCharacters.get("stark") + 1);

		}
		if (Pattern.matches(lannister, allTexts)) {
			calledCharacters.put("lannister", calledCharacters.get("lannister") + 1);

		}
		if (Pattern.matches(targaryen, allTexts)) {
			calledCharacters.put("targaryen", calledCharacters.get("targaryen") + 1);

		}
		if (Pattern.matches(tyrell, allTexts)) {
			calledCharacters.put("tyrell", calledCharacters.get("tyrell") + 1);

		}
	}
}
