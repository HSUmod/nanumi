package com.nanumi.dto;

public class ApplicationDTO {
	private String articleNum, userid, state, postingTime;

	public ApplicationDTO() {

	}

	public ApplicationDTO(String articleNum, String userid, String state) {
		this.articleNum = articleNum;
		this.userid = userid;
		this.state = state;
	}

	public String getArticleNum() {
		return articleNum;
	}

	public void setArticleNum(String articleNum) {
		this.articleNum = articleNum;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostingTime() {
		return postingTime;
	}

	public void setPostingTime(String postingTime) {
		this.postingTime = postingTime;
	}

}
