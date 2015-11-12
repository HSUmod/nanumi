package com.nanumi.dto;

public class CityDTO {
	private int citycode, districtcode;
	private String city, district;

	public CityDTO() {

	}

	public int getCitycode() {
		return citycode;
	}

	public void setCitycode(int citycode) {
		this.citycode = citycode;
	}

	public int getDistrictcode() {
		return districtcode;
	}

	public void setDistrictcode(int districtcode) {
		this.districtcode = districtcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

}