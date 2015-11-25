package com.nanumi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nanumi.dto.FileDTO;
import com.nanumi.dto.GoodsDTO;

public interface GoodsService {
	public void writingGoods(GoodsDTO goods, HttpServletRequest request) throws Exception;

	public List<GoodsDTO> readGoods();

	public FileDTO selectFileInfo(String articleNum);
}
