package com.nanumi.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nanumi.dto.FileDTO;
import com.nanumi.dto.GoodsDTO;
import com.nanumi.dto.UserDTO;

@Repository("GoodsDAO")
public class GoodsDAO {

	@Autowired
	private SqlSession session;

	public void writingGoods(GoodsDTO goods) {
		session.update("nanumiNS.writingGoods", goods);
	}

	public void insertFile(Map<String, Object> map) throws Exception {
		session.insert("nanumiNS.insertFile", map);
	}

	public List<GoodsDTO> readGoods() {
		List<GoodsDTO> goodsList = new ArrayList<GoodsDTO>();

		goodsList = session.selectList("selectAllGoods");

		return goodsList;
	}
	
	public UserDTO getUserAddress(String userid) {
		return session.selectOne("nanumiNS.getUserAddress", userid);
	}

	public FileDTO selectFileInfo(String articleNum) {
		return session.selectOne("nanumiNS.selectFileInfo", articleNum);
	}
}
