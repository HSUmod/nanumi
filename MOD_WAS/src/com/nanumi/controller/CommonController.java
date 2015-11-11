package com.nanumi.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String signUp(@RequestParam("userid") String userid, @RequestParam("pwd") String pwd, @RequestParam("nickname") String nickname, @RequestParam("address") String address,
			@RequestParam("email") String email) {
		if (isDuplicateUserid(userid)) {
			return "{\"fail\": \"SIGNUP_ERROR_01\"}";
		}
		if (isDuplicateUserNickname(nickname)) {
			return "{\"fail\": \"SIGNUP_ERROR_02\"}";
		}
		if (isDuplicateUserEmail(email)) {
			return "{\"fail\": \"SIGNUP_ERROR_03\"}";
		}
		service.signUp(new UserDTO(userid, pwd, nickname, address, email));

		return "{\"success\": \"SIGNUP_COMPLETE\"}";
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
	 *  LOGIN_ERROR_01: 아이디 입력 안 함
	 *  LOGIN_ERROR_02: 비밀번호 틀림
	 *  LOGIN_ERROR_03: 존재하지 않는 아이디
	 */
	@RequestMapping(value = "/Login.do", method = RequestMethod.POST)
	public String login(@RequestParam("userid") String userid, @RequestParam("pwd") String pwd, HttpSession session) {
		if (userid == null) {
			return "{\"fail\": \"LOGIN_ERROR_01\"}";
		}

		UserDTO user = service.login(userid);
		try {
			if (user.getPwd().equals(pwd)) {
				String userUUID = generateUUID(user.getUserid()); // 로그인 성공, uuid 발급
				session.setAttribute("UUID-", userUUID); // session에 uuid 저장
				return userUUID; // User device에 uuid 전송
			} else {
				return "{\"fail\": \"LOGIN_ERROR_02\"}";
			}
		} catch (NullPointerException e) {
			return "{\"fail\": \"LOGIN_ERROR_03\"}";
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
}
