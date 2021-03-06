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

@Service("GoodsService")
public class GoodsServiceImpl implements GoodsService {
	Logger log = Logger.getLogger(this.getClass());
	@Resource(name = "fileUtils")
	private FileUtils fileUtils;
	@Autowired
	private GoodsDAO goodsDAO;
	@Autowired
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
	public List<GoodsDTO> readGoods() {
		return goodsDAO.readGoods();
	}

	@Override
	public List<GoodsDTO> searchGoodsByCity(String city) {
		return goodsDAO.searchGoodsByCity(city);
	}

	@Override
	public List<GoodsDTO> searchGoodsByDistrict(String district) {
		return goodsDAO.searchGoodsByDistrict(district);
	}

	@Override
	public List<GoodsDTO> searchGoodsByAddress(String city, String district) {
		return goodsDAO.searchGoodsByAddress(city, district);
	}

	@Override
	public FileDTO selectFileInfo(String articleNum) {
		return goodsDAO.selectFileInfo(articleNum);
	}

	@Override
	public List<ApplicationDTO> getApplicationList() {
		return applicationDAO.getApplicationList();
	}

	@Override
	public List<ApplicationDTO> getMyGoodsApplicationList(String aritcleNum) {
		return applicationDAO.getMyGoodsApplicationList(aritcleNum);
	}

	@Override
	public void apply(String articleNum, String userid) {
		applicationDAO.apply(articleNum, userid);
	}

	@Override
	public void applyCancle(String articleNum, String userid) {
		applicationDAO.applyCancle(articleNum, userid);
	}

	@Override
	public void choice(String articleNum, String userid) {
		applicationDAO.choice(articleNum, userid);
		goodsDAO.choice(articleNum);
	}

	@Override
	public void choiceCancle(String articleNum) {
		applicationDAO.choiceCancle(articleNum);
		goodsDAO.choiceCancle(articleNum);
	}

	@Override
	public void donateComplete(String articleNum) {
		applicationDAO.donateComplete(articleNum);
		goodsDAO.donateComplete(articleNum);
	}
}
