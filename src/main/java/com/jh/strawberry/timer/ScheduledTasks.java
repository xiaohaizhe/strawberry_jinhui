package com.jh.strawberry.timer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
	
	@Scheduled(cron = "0 */15 * * * ?")//每15min执行一次
	public void reportCurrentTime() {
		logger.debug("现在时间是："+sdf.format(new Date()));
		deviceService.updateDevice("504626770");
		deviceService.updateData("504626770");
	}

}

