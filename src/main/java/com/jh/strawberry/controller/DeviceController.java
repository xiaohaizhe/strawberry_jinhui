package com.jh.strawberry.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jh.strawberry.dto.Device;
import com.jh.strawberry.repository.DeviceRepository;
import com.jh.strawberry.service.DeviceService;

import cmcc.iot.onenet.javasdk.response.BasicResponse;

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

	@RequestMapping("save")
	public void save() {
		Device device = new Device();
		device.setId("504626770");
		device.setOnline(true);
		device.setTime(new Date());
		deviceRepository.save(device);
	}
	
	@RequestMapping("find")
	public void getByDeviceSn(String id) {
		Optional<Device> optional = deviceRepository.findById(id);
		System.out.println(optional.isPresent());
	}
    
    
	@RequestMapping("status")
	public void getDeviceStatus(String device_sn) {
		deviceService.updateData(device_sn);
	}
	
	@RequestMapping(value = "get_latest_data",method = RequestMethod.GET)
	public JSONObject getLatestData(String id) {
		return deviceService.getLatestData(id);
	}
	@RequestMapping(value = "get_data_in_chart",method = RequestMethod.GET)
	public JSONObject getDataInChart(String id,String type) {
		return null;
	}
	
	

}

