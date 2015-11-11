package com.nanumi.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.nanumi.dao.UserDAO;
import com.nanumi.dto.UserDTO;

public class CommonService {
	@Autowired
	private UserDAO userDAO;

	public UserDTO login(String userid) {
		return userDAO.login(userid);
	}
}
