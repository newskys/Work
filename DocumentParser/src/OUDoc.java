import java.util.Date;

/**
 * 오늘의 유머 게시글 정보를 담는 클래스
 * @author Vinckel
 * @version 20140518
 *
 */
public class OUDoc {
	private String rawHtml;
	private String title;
	private String content;
	private String Writer;
	private Date writeTime;
	private int goodCount;
	private int badCount;
	private int lateCount;
	private int readCount;
	private int replyCount;
	
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
		return Writer;
	}
	public void setWriter(String writer) {
		Writer = writer;
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
}
