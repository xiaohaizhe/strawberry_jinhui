package com.jh.strawberry.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pyt
 * @createTime 2018年12月4日下午3:07:33
 */
public class SoilHumidity extends BaseDto implements Serializable {

	/**
	 * 土壤湿度
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "SoilHumidity :" + super.toString();
	}
	
}

