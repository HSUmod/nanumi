package com.nanumi.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@RequestMapping("SignUp.do")
	public void signUp(@RequestParam("userid") String userid) {
		if (isDuplicateUserid(userid)) {
			// id 중복, 가입 불가
		}

	}

	private boolean isDuplicateUserid(String userid) {
		if (service.login(userid) != null) {
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
	@RequestMapping("Login.do")
	public String login(@RequestParam("userid") String userid, @RequestParam("pwd") String pwd, HttpSession session) {
		if (userid == null) {
			return "LOGIN_ERROR_01";
		}

		UserDTO user = service.login(userid);
		try {
			if (user.getPwd().equals(pwd)) {
				String userUUID = generateUUID(user.getUserid()).toString(); // 로그인 성공, uuid 발급
				session.setAttribute("UUID_" + userid, userUUID); // session에 uuid 저장
				return userUUID; // User device에 uuid 전송
			} else {
				return "LOGIN_ERROR_02";
			}
		} catch (NullPointerException e) {
			return "LOGIN_ERROR_03";
		}
	}

	@RequestMapping("Logout.do")
	public void logout(@RequestParam("userid") String userid, HttpSession session) {
		session.removeAttribute("UUID_" + userid);
	}

	/**
	 * User ID로 uuid 생성
	 * @param userid
	 * @return uuid
	 */
	private UUID generateUUID(String userid) {
		return UUID.fromString(userid);
	}
}
