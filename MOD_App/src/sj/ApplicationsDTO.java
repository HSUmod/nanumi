package sj;

public class ApplicationsDTO {
	private String articleNum, state, userid, time;	
	public ApplicationsDTO(String articleNum, String state, String userid, String time) {
		this.articleNum = articleNum;
		this.state = state;
		this.userid = userid;
		this.time = time;
	}
	public String getArticleNum() {
		return articleNum;
	}
	public void setArticleNum(String articleNum) {
		this.articleNum = articleNum;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;	
	}
	
}
