package com.nanumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nanumi.dao.UserDAO;
import com.nanumi.dto.UserDTO;

@Service("CommonService")
public class CommonService {
	@Autowired
	private UserDAO userDAO;

	public void signUp(UserDTO user) {
		userDAO.signUp(user);
	}

	public UserDTO checkUserId(String userid) {
		return userDAO.checkUserId(userid);
	}

	public UserDTO checkNickname(String nickname) {
		return userDAO.checkNickname(nickname);
	}

	public UserDTO checkEmail(String email) {
		return userDAO.checkNickname(email);
	}

	public UserDTO login(String userid) {
		return userDAO.login(userid);
	}
}
