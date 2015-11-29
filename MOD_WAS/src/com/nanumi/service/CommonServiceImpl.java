package com.nanumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nanumi.dao.AddressDAO;
import com.nanumi.dao.UserDAO;
import com.nanumi.dto.AddressDTO;
import com.nanumi.dto.CityDTO;
import com.nanumi.dto.DistrictDTO;
import com.nanumi.dto.UserDTO;

@Service("CommonService")
public class CommonServiceImpl implements CommonService {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private AddressDAO addressDAO;

	@Override
	public void signUp(UserDTO user) {
		userDAO.signUp(user);
	}

	@Override
	public UserDTO checkUserId(String userid) {
		return userDAO.checkUserId(userid);
	}

	@Override
	public UserDTO checkNickname(String nickname) {
		return userDAO.checkNickname(nickname);
	}

	@Override
	public UserDTO checkEmail(String email) {
		return userDAO.checkEmail(email);
	}

	@Override
	public UserDTO login(String userid) {
		return userDAO.login(userid);
	}

	@Override
	public List<CityDTO> getCities() {
		return addressDAO.getCities();
	}

	@Override
	public List<DistrictDTO> getDistricts() {
		return addressDAO.getDistricts();
	}

	@Override
	public String getUserIdByEmail(String email) {
		return userDAO.getUserIdByEmail(email);
	}

	@Override
	public void modifyUserInfo(UserDTO user) {
		userDAO.modifyInfo(user);
	}

	@Override
	public AddressDTO getUserAddress(String userid) {
		return addressDAO.getUserAddress(userid);
	}
}
