package com.jh.strawberry.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pyt
 * @createTime 2018年12月4日下午3:06:49
 */
public class co2 extends BaseDto implements Serializable {

	/**
	 * 二氧化碳浓度
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return "co2 :" + super.toString();
	}	
}

