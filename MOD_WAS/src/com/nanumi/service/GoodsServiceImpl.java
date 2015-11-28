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
import com.nanumi.dao.ApplicationDAO;
import com.nanumi.dao.GoodsDAO;
import com.nanumi.dto.ApplicationDTO;
import com.nanumi.dto.FileDTO;
import com.nanumi.dto.GoodsDTO;
import com.nanumi.dto.UserDTO;

@Service("GoodsService")
public class GoodsServiceImpl implements GoodsService {
	Logger log = Logger.getLogger(this.getClass());
	@Resource(name = "fileUtils")
	private FileUtils fileUtils;
	@Autowired
	private GoodsDAO goodsDAO;
	private ApplicationDAO applicationDAO;

	@Override
	public void writingGoods(GoodsDTO goods, HttpServletRequest request) throws Exception {
		goodsDAO.writingGoods(goods);

		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		MultipartFile multipartFile = null;
		while (iterator.hasNext()) {
			multipartFile = multipartHttpServletRequest.getFile(iterator.next());
			if (multipartFile.isEmpty() == false) {
				log.info("------------- file start -------------");
				log.info("name : " + multipartFile.getName());
				log.info("filename : " + multipartFile.getOriginalFilename());
				log.info("size : " + multipartFile.getSize());
				log.info("-------------- file end --------------\n");
			}
		}
		List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(goods, request);
		for (int i = 0, size = list.size(); i < size; i++) {
			goodsDAO.insertFile(list.get(i));
		}
	}

	@Override
	public UserDTO getUserAddress(String userid) {
		return goodsDAO.getUserAddress(userid);
	}

	@Override
	public List<GoodsDTO> readGoods() {
		return goodsDAO.readGoods();
	}

	@Override
	public FileDTO selectFileInfo(String articleNum) {
		return goodsDAO.selectFileInfo(articleNum);
	}

	@Override
	public List<ApplicationDTO> getMyApplicationList(String userid) {
		return applicationDAO.getMyApplicationList(userid);
	}

	@Override
	public List<ApplicationDTO> getMyGoodsApplicationList(String aritcleNum) {
		return applicationDAO.getMyGoodsApplicationList(aritcleNum);
	}

	@Override
	public void apply(String articleNum, String userid) {
		applicationDAO.apply(articleNum, userid);
	}
}
