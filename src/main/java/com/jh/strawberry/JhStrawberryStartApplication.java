package com.jh.strawberry;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author pyt
 * @createTime 2018年12月6日上午9:49:32
 */
public class JhStrawberryStartApplication extends SpringBootServletInitializer {
	 @Override
	 protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		 return builder.sources(JhStrawberryApplication.class);
	 }	
}