package com.jh.strawberry.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pyt
 * @createTime 2018年12月4日下午3:06:03
 */
public class Illuminance extends BaseDto implements Serializable {

	/**
	 * 光照强度
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Illuminance :" + super.toString();
	}
	
}

