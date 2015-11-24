package com.nanumi.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nanumi.common.FileUtils;
import com.nanumi.dao.GoodsDAO;
import com.nanumi.dto.GoodsDTO;

@Service("GoodsService")
public class GoodsServiceImpl implements GoodsService {
	Logger log = Logger.getLogger(this.getClass());
	@Resource(name = "fileUtils")
	private FileUtils fileUtils;
	@Autowired
	private GoodsDAO goodsDAO;

	@Override
	public void writingGoods(GoodsDTO goods, HttpServletRequest request) throws Exception {
		goodsDAO.writingGoods(goods);

		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		MultipartFile multipartFile = null;
		while (iterator.hasNext()) {
			multipartFile = multipartHttpServletRequest.getFile(iterator.next());
			if (multipartFile.isEmpty() == false) {
				log.debug("------------- file start -------------");
				log.debug("name : " + multipartFile.getName());
				log.debug("filename : " + multipartFile.getOriginalFilename());
				log.debug("size : " + multipartFile.getSize());
				log.debug("-------------- file end --------------\n");
			}
		}
		List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(goods, request);
		for (int i = 0, size = list.size(); i < size; i++) {
			goodsDAO.insertFile(list.get(i));
		}
	}
}
