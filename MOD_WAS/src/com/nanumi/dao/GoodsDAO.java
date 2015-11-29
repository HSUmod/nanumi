package com.nanumi.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nanumi.dto.AddressDTO;
import com.nanumi.dto.FileDTO;
import com.nanumi.dto.GoodsDTO;

@Repository("GoodsDAO")
public class GoodsDAO {

	@Autowired
	private SqlSession session;

	public void writingGoods(GoodsDTO goods) {
		session.update("nanumiNS.writingGoods", goods);
	}

	public void insertFile(Map<String, Object> map) throws Exception {
		session.insert("nanumiNS.insertFile", map);
	}

	public List<GoodsDTO> readGoods() {
		List<GoodsDTO> goodsList = new ArrayList<GoodsDTO>();

		goodsList = session.selectList("selectAllGoods");

		return goodsList;
	}

	public List<GoodsDTO> searchGoodsByCity(String city) {
		List<GoodsDTO> goodsList = new ArrayList<GoodsDTO>();

		goodsList = session.selectList("searchGoodsByCity", city);

		return goodsList;
	}

	public List<GoodsDTO> searchGoodsByDistrict(String district) {
		List<GoodsDTO> goodsList = new ArrayList<GoodsDTO>();

		goodsList = session.selectList("searchGoodsByDistrict", district);

		return goodsList;
	}

	public List<GoodsDTO> searchGoodsByAddress(String city, String district) {
		List<GoodsDTO> goodsList = new ArrayList<GoodsDTO>();
		AddressDTO obj = new AddressDTO(city, district);

		goodsList = session.selectList("searchGoodsByAddress", obj);

		return goodsList;
	}

	public FileDTO selectFileInfo(String articleNum) {
		return session.selectOne("nanumiNS.selectFileInfo", articleNum);
	}

	public void choice(String articleNum) {
		GoodsDTO obj = new GoodsDTO(articleNum, "1");

		session.update("choice_goods", obj);
	}
	
	public void choiceCancle(String articleNum) {
		GoodsDTO obj = new GoodsDTO(articleNum, "0");

		session.update("choiceCancle_goods", obj);
	}
}
