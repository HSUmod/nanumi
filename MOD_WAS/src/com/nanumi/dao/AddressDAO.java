package com.nanumi.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nanumi.dto.CityDTO;
import com.nanumi.dto.DistrictDTO;

@Repository("AddressDAO")
public class AddressDAO {
	@Autowired
	private SqlSession session;

	public List<CityDTO> getCities() {
		List<CityDTO> cityList = new ArrayList<CityDTO>();

		cityList = session.selectList("nanumiNS.cityList");

		return cityList;
	}

	public List<DistrictDTO> getDistricts() {
		List<DistrictDTO> districtList = new ArrayList<DistrictDTO>();

		districtList = session.selectList("nanumiNS.districtList");

		return districtList;
	}

}
