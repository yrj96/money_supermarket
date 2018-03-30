package me.sunlight.sdk.common.model;

import java.io.Serializable;
import java.util.List;

public class ProvinceModel implements Serializable {
	private String name;
	private String id;
	private List<CityModel> cityList;
	
	public ProvinceModel() {
		super();
	}

	public ProvinceModel(String name,String id, List<CityModel> cityList) {
		super();
		this.name = name;
		this.cityList = cityList;
		this.id=id;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CityModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityModel> cityList) {
		this.cityList = cityList;
	}

	@Override
	public String toString() {
		return "ProvinceModel{" +
				"name='" + name + '\'' +
				", id='" + id + '\'' +
				", cityList=" + cityList +
				'}';
	}
}
