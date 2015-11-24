package com.nanumi.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nanumi.common.CommonUtils;
import com.nanumi.dto.CityDTO;
import com.nanumi.dto.DistrictDTO;
import com.nanumi.dto.UserDTO;
import com.nanumi.service.CommonService;

@Controller
public class CommonController {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private CommonService service;

	@RequestMapping(value = "/SignUp.do", method = RequestMethod.POST)
	public void signUp(@RequestParam("userid") String userid, @RequestParam("pwd") String pwd, @RequestParam("nickname") String nickname, @RequestParam("address") String address,
			@RequestParam("email") String email, HttpServletResponse res) throws IOException {
		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();

		service.signUp(new UserDTO(userid, pwd, nickname, address, email));
		pw.write("{\"result\": \"SIGNUP_COMPLETE\"}");
		pw.close();
	}

	@RequestMapping(value = "/UserIDCheck.do", method = RequestMethod.POST)
	public void isDuplicatedUserid(@RequestParam("userid") String userid, HttpServletResponse res) throws IOException {
		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();

		if (service.checkUserId(userid) != null) {
			pw.write("{\"result\": \"duplicated\"}");
		} else {
			pw.write("{\"result\": \"ok\"}");
		}

		pw.close();
	}

	@RequestMapping(value = "/UserNicknameCheck.do", method = RequestMethod.POST)
	private void isDuplicateUserNickname(@RequestParam("nickname") String nickname, HttpServletResponse res) throws IOException {
		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();

		if (service.checkNickname(nickname) != null) {
			pw.write("{\"result\": \"duplicated\"}");
		} else {
			pw.write("{\"result\": \"ok\"}");
		}

		pw.close();
	}

	@RequestMapping(value = "/UserEmailCheck.do", method = RequestMethod.POST)
	private void isDuplicateUserEmail(@RequestParam("email") String email, HttpServletResponse res) throws IOException {
		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();

		if (service.checkEmail(email) != null) {
			pw.write("{\"result\": \"duplicated\"}");
		} else {
			pw.write("{\"result\": \"ok\"}");
		}

		pw.close();
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
				String userUUID = CommonUtils.generateUUID(user.getUserid()); // 로그인 성공, uuid 발급
				session.setAttribute("UUID-", userUUID); // session에 uuid 저장
				pw.write("{\"result\": \"Success\", \"value\": \"" + userUUID + "\"}");
				log.debug("Login success: " + userUUID);
			} else {
				pw.write("{\"result\": \"Fail\", \"value\": \"1\"}");
				log.debug("Login fail: 01 " + userid);
			}
		} catch (NullPointerException e) {
			pw.write("{\"result\": \"Fail\", \"value\": \"2\"}");
			log.debug("Login fail: 02 " + userid);
		} finally {
			pw.close();
		}
	}

	@RequestMapping(value = "/Logout.do", method = RequestMethod.POST)
	public void logout(@RequestParam("uuid") String uuid, HttpSession session, HttpServletResponse res) throws IOException {
		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();

		try {
			session.removeAttribute("UUID-" + uuid);
			
			log.debug("Logout success: " + uuid);
			pw.write("{\"result\": \"Success\"}");
		} catch (IllegalStateException e) {
			log.debug("Logout fail: " + uuid);
			pw.write("{\"result\": \"Fail\"}");
		} finally {
			pw.close();
		}
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

	@RequestMapping(value = "/SearchUserID.do", method = RequestMethod.POST)
	public void searchUserid(@RequestParam("email") String email, HttpServletResponse res) throws IOException {
		String result = service.getUserIdByEmail(email);
		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();
		pw.write("{\"result\": \"" + result + "\"}");
		pw.close();
	}

	@RequestMapping(value = "/ModifyUserInfo.do", method = RequestMethod.POST)
	public void modifyUserInfo(@RequestParam("userid") String userid, @RequestParam("pwd") String pwd, @RequestParam("nickname") String nickname, @RequestParam("address") String address,
			@RequestParam("email") String email, HttpServletResponse res) throws IOException {
		service.modifyUserInfo(new UserDTO(userid, pwd, nickname, address, email));
		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();
		pw.write("{\"result\": \"MODIFY_COMPLETE\"}");
		pw.close();
	}
}