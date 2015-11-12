package com.nanumi.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nanumi.dto.UserDTO;

@Repository("UserDAO")
public class UserDAO {
	@Autowired
	private SqlSession session;

	public void signUp(UserDTO user) {
		session.update("nanumiNS.signUp", user);
	}

	public UserDTO checkUserId(String userid) {
		return session.selectOne("nanumiNS.checkUserId", userid);
	}

	public UserDTO checkNickname(String nickname) {
		return session.selectOne("nanumiNS.checkUserNickname", nickname);
	}

	public UserDTO checkEmail(String email) {
		return session.selectOne("nanumiNS.checkUserEmail", email);
	}

	public UserDTO login(String userid) {
		return session.selectOne("nanumiNS.login", userid);
	}

	public String getUserIdByEmail(String email) {
		return session.selectOne("nanumiNS.getUserIdByEmail", email);
	}
}
