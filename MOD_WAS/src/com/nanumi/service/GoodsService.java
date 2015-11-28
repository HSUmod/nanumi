package com.nanumi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nanumi.dto.ApplicationDTO;
import com.nanumi.dto.FileDTO;
import com.nanumi.dto.GoodsDTO;

public interface GoodsService {
	public void writingGoods(GoodsDTO goods, HttpServletRequest request) throws Exception;

	public List<GoodsDTO> readGoods();

	public FileDTO selectFileInfo(String articleNum);

	public List<ApplicationDTO> getMyApplicationList(String userid);

	public List<ApplicationDTO> getMyGoodsApplicationList(String aritcleNum);

	public void apply(String articleNum, String userid);

	public void applyCancle(String articleNum, String userid);
}
