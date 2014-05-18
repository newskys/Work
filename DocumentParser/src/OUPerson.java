import java.util.List;


/**
 * 오늘의 유머 회원 정보를 담는 클래스
 * @author Vinckel
 * @version 20140519
 */
public class OUPerson {
//	private int memberID;
	private String nickname;
//	private List<Integer> Doc = null;
//	private List<Integer> Reply = null;
	
	private int docCount;
	private int replyCount;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getDocCount() {
		return docCount;
	}
	public void setDocCount(int DocCount) {
		this.docCount = DocCount;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int ReplyCount) {
		this.replyCount = ReplyCount;
	}
	public void addDocCount() {
		docCount++;
	}
	public void addReplyCount() {
		replyCount++;
	}
}
