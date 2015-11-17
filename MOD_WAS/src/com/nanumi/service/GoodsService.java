package com.nanumi.service;

import javax.servlet.http.HttpServletRequest;

import com.nanumi.dto.GoodsDTO;

public interface GoodsService {
	public void writingGoods(GoodsDTO goods, HttpServletRequest request) throws Exception;
}
