package com.nanumi.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nanumi.dto.GoodsDTO;

@Repository("GoodsDAO")
public class GoodsDAO {

	@Autowired
	private SqlSession session;

	public void writingGoods(GoodsDTO goods) {
		session.update("nanumiNS.writingGoods", goods);
	}
}
