package com.nanumi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nanumi.dto.ApplicationDTO;
import com.nanumi.dto.FileDTO;
import com.nanumi.dto.GoodsDTO;
import com.nanumi.dto.UserDTO;

public interface GoodsService {
	public void writingGoods(GoodsDTO goods, HttpServletRequest request) throws Exception;

	public UserDTO getUserAddress(String userid);

	public List<GoodsDTO> readGoods();

	public FileDTO selectFileInfo(String articleNum);

	public List<ApplicationDTO> getMyApplicationList(String userid);

	public List<ApplicationDTO> getMyGoodsApplicationList(String aritcleNum);
}
