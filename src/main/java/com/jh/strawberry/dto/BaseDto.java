package com.jh.strawberry.dto;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;

/**
 * @author pyt
 * @createTime 2018年12月10日上午10:35:34
 */
public class BaseDto{
	private double value;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date time;
	private String deviceid;

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
		return "BaseDto{" +
				"value=" + value +
				", time=" + time +
				", deviceid='" + deviceid + '\'' +
				'}';
	}
}

