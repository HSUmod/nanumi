package com.nanumi.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nanumi.dto.CityDTO;
import com.nanumi.dto.DistrictDTO;
import com.nanumi.dto.UserDTO;
import com.nanumi.service.CommonService;

/**
 * 가입
 * 찾기 - 아이디, 비밀번호
 * 수정
 * 탈퇴
 * 로그인
 * 로그아웃
 * 관심상품 - 등록, 삭제
 */
@Controller
public class CommonController {
	@Autowired
	private CommonService service;

	@RequestMapping(value = "/SignUp.do", method = RequestMethod.POST)
	public void signUp(@RequestParam("userid") String userid, @RequestParam("pwd") String pwd, @RequestParam("nickname") String nickname, @RequestParam("address") String address,
			@RequestParam("email") String email, HttpServletResponse res) throws IOException {
		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();
		boolean signUpFlag = false;

		if (isDuplicateUserid(userid)) {
			pw.write("{\"fail\": \"SIGNUP_ERROR_01\"}");
		} else if (isDuplicateUserNickname(nickname)) {
			pw.write("{\"fail\": \"SIGNUP_ERROR_02\"}");
		} else if (isDuplicateUserEmail(email)) {
			pw.write("{\"fail\": \"SIGNUP_ERROR_03\"}");
		} else {
			signUpFlag = !signUpFlag;
		}

		if (signUpFlag) {
			service.signUp(new UserDTO(userid, pwd, nickname, address, email));
			pw.write("{\"success\": \"SIGNUP_COMPLETE\"}");
		}
		pw.close();
	}

	private boolean isDuplicateUserid(String userid) {
		if (service.checkUserId(userid) != null) {
			return true;
		}

		return false;
	}

	private boolean isDuplicateUserNickname(String nickname) {
		if (service.checkNickname(nickname) != null) {
			return true;
		}

		return false;
	}

	private boolean isDuplicateUserEmail(String email) {
		if (service.checkEmail(email) != null) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @return
	 * Success
	 *  uuid
	 * Fail
	 *  LOGIN_ERROR_01: 비밀번호 틀림
	 *  LOGIN_ERROR_02: 존재하지 않는 아이디 
	 */
	@RequestMapping(value = "/Login.do", method = RequestMethod.POST)
	public void login(@RequestParam("userid") String userid, @RequestParam("pwd") String pwd, HttpSession session, HttpServletResponse res) throws IOException {
		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();

		UserDTO user = service.login(userid);
		try {
			if (user.getPwd().equals(pwd)) {
				String userUUID = generateUUID(user.getUserid()); // 로그인 성공, uuid 발급
				session.setAttribute("UUID-", userUUID); // session에 uuid 저장
				pw.write(userUUID);
			} else {
				pw.write("{\"fail\": \"LOGIN_ERROR_01\"}");
			}
		} catch (NullPointerException e) {
			pw.write("{\"fail\": \"LOGIN_ERROR_02\"}");
		} finally {
			pw.close();
		}
	}

	@RequestMapping(value = "/Logout.do", method = RequestMethod.POST)
	public void logout(@RequestParam("userid") String userid, HttpSession session) {
		session.removeAttribute("UUID_" + userid);
	}

	/**
	 * User ID로 uuid 생성
	 * @param userid
	 * @return uuid
	 */
	private String generateUUID(String userid) {
		return userid + "-" + UUID.randomUUID();
	}

	@RequestMapping(value = "/SearchAddress.do")
	public void searchAddress(HttpServletResponse res) throws IOException {
		Map<String, List<String>> addressList = new HashMap<String, List<String>>();
		List<CityDTO> cityList = service.getCities();
		List<DistrictDTO> districtList = service.getDistricts();
		
		for (CityDTO city : cityList) {
			List<String> districts = new ArrayList<String>();

			for (DistrictDTO district : districtList) {
				if (city.getCitycode() == district.getCitycode()) {
					districts.add(district.getDistrict());
				}
			}
			addressList.put(city.getCity(), districts);
		}
		
		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();
		pw.write(addressList.toString());
		pw.close();
	}
}
