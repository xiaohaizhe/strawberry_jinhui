package com.jh.strawberry.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jh.strawberry.dto.Device;
import com.jh.strawberry.service.DeviceService;

/**
 * @author pyt
 * @createTime 2018年12月4日上午11:56:55
 */
@Component
public class ScheduledTasks {
	@Autowired
	private DeviceService deviceService;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static Logger logger = LogManager.getLogger(ScheduledTasks.class);
	
	private static String deviceid = "504626770";
	
	@Scheduled(cron = "0 */15 * * * ?")//每15min执行一次
	public void reportCurrentTime() {
		logger.debug("现在时间是："+sdf.format(new Date()));
		JSONObject object = deviceService.getDeviceById(deviceid);
		if((int)object.get("code")==0) {
			Device device =  (Device) object.get("data");
			Date start =device.getTime();
			Date end = new Date();
			try {
				deviceService.updateData(deviceid,start,end);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.debug(e.getMessage());
			}
		}		
		deviceService.updateDevice(deviceid);
	}

}

