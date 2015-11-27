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

	public List<ApplicationDTO> getMyApplicationList(String userid) {
		return session.selectList("getMyApplicationList", userid);
	}

	public List<ApplicationDTO> getMyGoodsApplicationList(String aritcleNum) {
		return session.selectList("getMyGoodsApplicationList", aritcleNum);
	}

}
