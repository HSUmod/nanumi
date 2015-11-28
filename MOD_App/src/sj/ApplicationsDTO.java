package sj;

public class ApplicationsDTO {
	private String articleNum, state, userid;	
	public ApplicationsDTO(String articleNum, String state, String userid) {
		this.articleNum = articleNum;
		this.state = state;
		this.userid = userid;
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
	
	
}
