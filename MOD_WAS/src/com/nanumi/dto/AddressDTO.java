package com.nanumi.dto;

public class AddressDTO {
	private String city, district;

	public AddressDTO(String city, String district) {
		this.city = city;
		this.district = district;
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
