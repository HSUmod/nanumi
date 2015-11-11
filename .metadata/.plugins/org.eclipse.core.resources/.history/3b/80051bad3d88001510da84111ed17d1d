package com.nanumi.dao;

import org.apache.ibatis.session.SqlSession;

import com.nanumi.dto.UserDTO;

public class UserDAO {
	private SqlSession session;

	public UserDTO login(String userid) {
		return session.selectOne("nanumiNS.login", userid);
	}

}
