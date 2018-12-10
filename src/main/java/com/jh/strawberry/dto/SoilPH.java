package com.jh.strawberry.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pyt
 * @createTime 2018年12月4日下午3:10:29
 */
public class SoilPH extends BaseDto implements Serializable {

	/**
	 * 土壤酸碱值
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return "AirHumidity :" + super.toString();
	}
}

