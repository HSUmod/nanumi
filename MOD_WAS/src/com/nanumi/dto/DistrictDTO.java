package com.nanumi.dto;

public class DistrictDTO {
	private int districtcode, citycode;
	private String district;

	public DistrictDTO() {

	}

	public int getDistrictcode() {
		return districtcode;
	}

	public void setDistrictcode(int districtcode) {
		this.districtcode = districtcode;
	}

	public int getCitycode() {
		return citycode;
	}

	public void setCitycode(int citycode) {
		this.citycode = citycode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

}