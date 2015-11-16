package com.nanumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nanumi.dao.GoodsDAO;
import com.nanumi.dto.GoodsDTO;

@Service("GoodsService")
public class GoodsService {
	@Autowired
	private GoodsDAO goodsDAO;

	public void writingGoods(GoodsDTO goods) {
		goodsDAO.writingGoods(goods);
	}
}
