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
		return session.selectOne("nanumiNS.checkUserid", userid);
	}

	public UserDTO checkNickname(String nickname) {
		return session.selectOne("nanumiNS.checkNickname", nickname);
	}

	public UserDTO checkEmail(String email) {
		return session.selectOne("nanumiNS.checkEmail", email);
	}

	public UserDTO login(String userid) {
		return session.selectOne("nanumiNS.login", userid);
	}

}
