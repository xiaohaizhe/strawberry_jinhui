package com.jh.strawberry.model;
/**
 * @author pyt
 * @createTime 2018年12月5日下午6:17:50
 */
public enum Datastream {
	AIR_TEMPERATURE(1,"空气温度"),
	AIR_HUMIDITY(2,"空气湿度"),
	ILLUMINANCE(3,"光照强度"),
	CO2(4,"二氧化碳浓度"),
	SOIL_HUMIDITY(5,"土壤湿度"),
	SOIL_TEMPERATURE(6,"土壤温度"),
	SOIL_PH(7,"土壤酸碱值");
	
	private int code;
	private String desc;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	private Datastream(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}	
}

