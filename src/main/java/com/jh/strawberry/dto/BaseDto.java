package com.jh.strawberry.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author pyt
 * @createTime 2018年12月10日上午10:35:34
 */
public class BaseDto{
	private double value;
	private Date time;
	private String deviceid;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	@Override
	public String toString() {
		return "[value=" + value + ", time=" + sdf.format(time) + ", deviceid=" + deviceid + "]";
	}
	
}

