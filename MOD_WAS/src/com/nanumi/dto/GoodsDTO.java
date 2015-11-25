package com.nanumi.dto;

public class GoodsDTO {
	private String articleNum;
	private String userid;
	private String contents, hashtag;
	private int citycode, districtcode, majorcode, subcode;
	private String city, district, major, sub, selectionWay;
	private int shareState;
	private String postingTime;
	private String ruserid;

	public GoodsDTO() {
	}

	public GoodsDTO(String userid, String contents, String city, String district, String major, String sub, String selectionWay, String hashtag) {
		this.userid = userid;
		this.contents = contents;
		this.city = city;
		this.district = district;
		this.major = major;
		this.sub = sub;
		this.selectionWay = selectionWay;
		this.hashtag = hashtag;
	}

	public GoodsDTO(String articleNum, String userid, String contents, String city, String district, String major, String sub, String selectionWay, String hashtag, String postingTime) {
		this.articleNum = articleNum;
		this.userid = userid;
		this.city = city;
		this.district = district;
		this.major = major;
		this.sub = sub;
		this.contents = contents;
		this.hashtag = hashtag;
		this.selectionWay = selectionWay;
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

	public int getCitycode() {
		return citycode;
	}

	public void setCitycode(int citycode) {
		this.citycode = citycode;
	}

	public int getDistrictcode() {
		return districtcode;
	}

	public void setDistrictcode(int districtcode) {
		this.districtcode = districtcode;
	}

	public int getMajorcode() {
		return majorcode;
	}

	public void setMajorcode(int majorcode) {
		this.majorcode = majorcode;
	}

	public int getSubcode() {
		return subcode;
	}

	public void setSubcode(int subcode) {
		this.subcode = subcode;
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

	public String getSelectionWay() {
		return selectionWay;
	}

	public void setSelectionWay(String selectionWay) {
		this.selectionWay = selectionWay;
	}

	public int getShareState() {
		return shareState;
	}

	public void setShareState(int shareState) {
		this.shareState = shareState;
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