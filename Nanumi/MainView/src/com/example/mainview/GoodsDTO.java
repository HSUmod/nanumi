package com.example.mainview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class GoodsDTO {
	private String articleNum, userid, city, district, major, sub, contents, hashtag, selectionWay, postingTime;
	private Bitmap img;

	public GoodsDTO() {
		super();
	}

	public GoodsDTO(String articleNum, String userid, String city, String district, String major, String sub,
			String contents, String hashtag, String selectionWay, String postingTime, byte[] img) {
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
		this.img = BitmapFactory.decodeByteArray(img, 0, img.length);
	}

	public GoodsDTO(String articleNum, String userid, String city, String district, String major, String sub,
			String contents, String hashtag, String selectionWay, String postingTime) {
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

	public GoodsDTO(String contents, String hashtag) {
		super();
		this.contents = contents;
		this.hashtag = hashtag;
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

	public String getPostingTime() {
		return postingTime;
	}

	public void setPostingTime(String postingTime) {
		this.postingTime = postingTime;
	}

	public Bitmap getImg() {
		return img;
	}

	public void setImg(Bitmap img) {
		this.img = img;
	}

}