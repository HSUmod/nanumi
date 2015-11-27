package com.nanumi.dto;

public class GoodsDTO {
	private String articleNum, userid, city, district, major, sub;
	private String contents, hashtag, selectionWay, state, postingTime;
	private String ruserid;

	public GoodsDTO() {
		
	}

	public GoodsDTO(String articleNum, String userid, String city, String district, String major, String sub, String contents, String hashtag, String selectionWay, String state, String postingTime) {
		this.articleNum = articleNum;
		this.userid = userid;
		this.city = city;
		this.district = district;
		this.major = major;
		this.sub = sub;
		this.contents = contents;
		this.hashtag = hashtag;
		this.selectionWay = selectionWay;
		this.state = state;
		this.postingTime = postingTime;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public String getSelectionWay() {
		return selectionWay;
	}

	public void setSelectionWay(String selectionWay) {
		this.selectionWay = selectionWay;
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

	public String getRuserid() {
		return ruserid;
	}

	public void setRuserid(String ruserid) {
		this.ruserid = ruserid;
	}

}