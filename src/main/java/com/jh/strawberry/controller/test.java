package com.jh.strawberry.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pyt
 * @createTime 2018年12月4日下午2:50:26
 */
@RestController
@RequestMapping("api")
public class test {
	@RequestMapping("test")
	public String test() {
		return "Success!!!";
	}

}

