package com.nanumi.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nanumi.dto.ApplicationDTO;

@Repository("ApplicationDAO")
public class ApplicationDAO {
	@Autowired
	private SqlSession session;

	public List<ApplicationDTO> getApplicationList() {
		return session.selectList("getApplicationList");
	}

	public List<ApplicationDTO> getMyGoodsApplicationList(String aritcleNum) {
		return session.selectList("getMyGoodsApplicationList", aritcleNum);
	}

	public void apply(String articleNum, String userid) {
		ApplicationDTO obj = new ApplicationDTO(articleNum, userid, "0");

		session.insert("apply", obj);
	}

	public void applyCancle(String articleNum, String userid) {
		ApplicationDTO obj = new ApplicationDTO(articleNum, userid, "0");

		session.delete("applyCancle", obj);
	}
	
	public void choice(String articleNum, String userid) {
		ApplicationDTO obj = new ApplicationDTO(articleNum, userid, "1");

		session.update("choice_apply", obj);
	}
	
	public void choiceCancle(String articleNum) {
		ApplicationDTO obj = new ApplicationDTO(articleNum, "0");

		session.update("choiceCancle_apply", obj);
	}

}
