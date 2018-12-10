package com.jh.strawberry.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pyt
 * @createTime 2018年12月4日下午3:09:55
 */
public class SoilTemperature extends BaseDto implements Serializable {

	/**
	 *土壤温度 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return "SoilTemperature :" + super.toString();
	}
	
}

