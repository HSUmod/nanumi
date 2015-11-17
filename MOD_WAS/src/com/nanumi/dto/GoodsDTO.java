package com.nanumi.dto;

public class GoodsDTO {
	private String articleNum;
	private String userid;
	private String image1, image2, image3, image4, image5;
	private String contents, hashtag;
	private int citycode, districtcode, majorcode, subcode;
	private String city, district, major, sub;
	private int selectionWay, shareState;
	private String postingTime;
	private String ruserid;

	public GoodsDTO() {
	}

	public GoodsDTO(String userid, String contents, String city, String district, String major, String sub, int selectionWay) {
		this.userid = userid;
		this.contents = contents;
		this.city = city;
		this.district = district;
		this.major = major;
		this.sub = sub;
		this.selectionWay = selectionWay;
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

	public String getImage1url() {
		return image1;
	}

	public void setImage1url(String image1url) {
		this.image1 = image1url;
	}

	public String getImage2url() {
		return image2;
	}

	public void setImage2url(String image2url) {
		this.image2 = image2url;
	}

	public String getImage3url() {
		return image3;
	}

	public void setImage3url(String image3url) {
		this.image3 = image3url;
	}

	public String getImage4url() {
		return image4;
	}

	public void setImage4url(String image4url) {
		this.image4 = image4url;
	}

	public String getImage5url() {
		return image5;
	}

	public void setImage5url(String image5url) {
		this.image5 = image5url;
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

	public int getSelectionWay() {
		return selectionWay;
	}

	public void setSelectionWay(int selectionWay) {
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