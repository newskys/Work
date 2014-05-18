import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 오늘의 유머 게시글 정보를 담는 클래스
 * @author Vinckel
 * @version 20140518
 *
 */
public class OUDoc {
	private final static DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private String rawHtml;
	private String title;
	private String content;
	private String writer;
	private Date writeTime;
	private int goodCount;
	private int badCount;
	private int lateCount;
	private int readCount;
	private int replyCount;
	private List<String> replyPeople;
	private List<String> goodPeople;
	
	public OUDoc(String rawHtml) {
		setRawHtml(rawHtml);
		replyPeople = new ArrayList<String>();
		goodPeople = new ArrayList<String>();
		try {
			calc();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	private void calc() throws ParseException {
		Document doc = Jsoup.parse(rawHtml);
		title = doc.select(".view_subject").text();
		content = doc.select(".view_content").text();
		writer = doc.select(".view_writer > a > font").text();
		readCount = Integer.parseInt(doc.select(".view_viewCount").text().replaceAll("[^0-9.,]+",""));
		replyCount = Integer.parseInt(doc.select(".view_replyCount").text().replaceAll("[^0-9.,]+",""));
		
		String rawDate = doc.select(".view_bestRegDate").text();
		writeTime = df.parse(rawDate);
		
		String[] okNokSplit = doc.select(".view_okNok:first-of-type").text().replaceAll("[^0-9.,]+"," ").split(" ");
		goodCount =Integer.parseInt(okNokSplit[0]);
		badCount =Integer.parseInt(okNokSplit[1]);
		
		String replyTexts = doc.select(".memo_name_class > a > font").text();
		
		if (replyTexts.length() > 0) {
			String[] replyPersonInfos = replyTexts.split(" ");
			
			for (int i = 0; i < replyPersonInfos.length; i++) {
				replyPeople.add(replyPersonInfos[i]);
			}
		}
		
		Elements script = doc.select("script");
		Pattern p = Pattern.compile("ok_list_total.*");
		Matcher m = p.matcher(script.html());
		if (m.find()) {
			String okListTotal = m.group(0);
			okListTotal = okListTotal.substring(okListTotal.indexOf("\"") + 1, okListTotal.lastIndexOf("\""));
			if (okListTotal.length() > 0) {
				calcJavascript(okListTotal);
			}
		}
	}
	private void calcJavascript(String okListTotal) {
		Document doc = Jsoup.parseBodyFragment(okListTotal);
		String[] goodPersonInfos = doc.select("body").text().split(" ");
		
		for (int i = 4; i < goodPersonInfos.length; i += 6) {
			goodPeople.add(goodPersonInfos[i].replaceAll(" ", ""));
		}
	}
	public String getRawHtml() {
		return rawHtml;
	}
	public void setRawHtml(String rawHtml) {
		this.rawHtml = rawHtml;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		writer = writer;
	}
	public Date getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}
	public int getGoodCount() {
		return goodCount;
	}
	public void setGoodCount(int goodCount) {
		this.goodCount = goodCount;
	}
	public int getBadCount() {
		return badCount;
	}
	public void setBadCount(int badCount) {
		this.badCount = badCount;
	}
	public int getLateCount() {
		return lateCount;
	}
	public void setLateCount(int lateCount) {
		this.lateCount = lateCount;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public List<String> getGoodPeople() {
		return goodPeople;
	}
//	public void setGoodPeople(List<String> goodPeople) {
//		this.goodPeople = goodPeople;
//	}
	public List<String> getReplyPeople() {
		return replyPeople;
	}
//	public void setReplyPeople(List<String> replyPeople) {
//		this.replyPeople = replyPeople;
//	}
}
