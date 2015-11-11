package com.nanumi.dto;

public class UserDTO {
	private String userid, pwd, nickname, address, email;

	public UserDTO() {
	}

	public UserDTO(String userid, String pwd, String nickname, String address, String email) {
		this.userid = userid;
		this.pwd = pwd;
		this.nickname = nickname;
		this.address = address;
		this.email = email;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
