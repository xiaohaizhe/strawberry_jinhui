package com.jh.strawberry.controller;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jh.strawberry.repository.DeviceRepository;
import com.jh.strawberry.service.DeviceService;

/**
 * @author pyt
 * @createTime 2018年12月4日上午11:16:17
 */
@RestController
@RequestMapping("/api/device")
public class DeviceController {
	@Autowired
	private DeviceService deviceService;
	@Autowired 
	private DeviceRepository deviceRepository;
	
	@RequestMapping(value = "get_latest_data",method = RequestMethod.GET)
	public JSONObject getLatestData(String id) {
		return deviceService.getLatestData(id);
	}
	@RequestMapping(value = "get_data_in_chart",method = RequestMethod.GET)
	public JSONObject getDataInChart(String id,String type) {
		return deviceService.getDataInChart(id, type);
	}
	
	@RequestMapping(value = "update",method = RequestMethod.GET)
	public void update(String id) {
		Date end = new Date();
		Date start = new Date();
		start.setDate(start.getDate()-3);
		try {
			deviceService.updateData(id, start, end);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

