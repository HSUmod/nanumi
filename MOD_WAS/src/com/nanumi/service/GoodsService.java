package com.nanumi.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nanumi.dto.GoodsDTO;

public interface GoodsService {
	public void writingGoods(GoodsDTO goods, HttpServletRequest request) throws Exception;

	public List<GoodsDTO> readGoods();

	public Map<String, Object> selectFileInfo(String articleNum);
}
