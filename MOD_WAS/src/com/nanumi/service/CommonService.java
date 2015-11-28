package com.nanumi.service;

import java.util.List;

import com.nanumi.dto.CityDTO;
import com.nanumi.dto.DistrictDTO;
import com.nanumi.dto.UserDTO;

public interface CommonService {
	public void signUp(UserDTO user);

	public UserDTO checkUserId(String userid);

	public UserDTO checkNickname(String nickname);

	public UserDTO checkEmail(String email);

	public UserDTO getUserAddress(String userid);

	public UserDTO login(String userid);

	public List<CityDTO> getCities();

	public List<DistrictDTO> getDistricts();

	public String getUserIdByEmail(String email);

	public void modifyUserInfo(UserDTO user);
}
