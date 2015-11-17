package com.nanumi.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nanumi.common.FileUtils;
import com.nanumi.dao.GoodsDAO;
import com.nanumi.dto.GoodsDTO;

@Service("GoodsService")
public class GoodsServiceImpl implements GoodsService {
	@Resource(name = "fileUtils")
	private FileUtils fileUtils;
	@Autowired
	private GoodsDAO goodsDAO;

	@Override
	public void writingGoods(GoodsDTO goods, HttpServletRequest request) throws Exception {
		goodsDAO.writingGoods(goods);

		List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(goods, request);
		for (int i = 0, size = list.size(); i < size; i++) {
			goodsDAO.insertFile(list.get(i));
		}
	}
}
