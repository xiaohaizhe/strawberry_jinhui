package com.jh.strawberry.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.config.RepositoryConfigurationDelegate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jh.strawberry.dto.AirHumidity;
import com.jh.strawberry.dto.AirTemperature;
import com.jh.strawberry.dto.BaseDto;
import com.jh.strawberry.dto.co2;
import com.jh.strawberry.model.RESCODE;
import com.jh.strawberry.dto.Device;
import com.jh.strawberry.dto.Illuminance;
import com.jh.strawberry.dto.SoilHumidity;
import com.jh.strawberry.dto.SoilPH;
import com.jh.strawberry.dto.SoilTemperature;
import com.jh.strawberry.repository.AirHumidityRepository;
import com.jh.strawberry.repository.AirTemperatureRepository;
import com.jh.strawberry.repository.CO2Repository;
import com.jh.strawberry.repository.DeviceRepository;
import com.jh.strawberry.repository.IlluminanceRepository;
import com.jh.strawberry.repository.SoilHumidityRepository;
import com.jh.strawberry.repository.SoilPHRepository;
import com.jh.strawberry.repository.SoilTemperatureRepository;
import com.jh.strawberry.utils.Config;
import com.jh.strawberry.utils.Constants;

import cmcc.iot.onenet.javasdk.api.datastreams.FindDatastreamListApi;
import cmcc.iot.onenet.javasdk.api.device.GetDeviceApi;
import cmcc.iot.onenet.javasdk.response.BasicResponse;
import cmcc.iot.onenet.javasdk.response.datastreams.DatastreamsResponse;
import cmcc.iot.onenet.javasdk.response.device.DeviceResponse;

/**
 * @author pyt
 * @createTime 2018年12月5日下午2:15:38
 */
@Service
@Transactional
public class DeviceService {
	@Autowired
	private DeviceRepository deviceRepository;
	@Autowired
	private AirHumidityRepository airHumidityRepository;
	@Autowired
	private AirTemperatureRepository airTemperatureRepository;
	@Autowired
	private CO2Repository co2Repository;
	@Autowired
	private IlluminanceRepository illuminanceRepository;
	@Autowired
	private SoilHumidityRepository soilHumidityRepository;
	@Autowired
	private SoilTemperatureRepository soilTemperatureRepository;
	@Autowired
	private SoilPHRepository soilPHRepository;
	
	private Logger logger = LogManager.getLogger(DeviceService.class);
	
	private static String api_key = Config.getString("api_key");
		
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 获取设备状态
	 * @param id
	 * @return
	 */
	public JSONObject getDeviceById(String id) {
		Optional<Device> optional = deviceRepository.findById(id);
		if(optional.isPresent()) {
			return RESCODE.SUCCESS.getJSONRES(optional.get());
		}
		return RESCODE.FAILURE.getJSONRES();
	}
	/**
	 * 获取设备最新数据
	 * @param id
	 * @return
	 */
	public JSONObject getLatestData(String id) {
		logger.debug("进入获取最新数据");
		JSONObject object = new JSONObject();
		Sort sort = new Sort("time");
		List<AirHumidity> airHumidities = airHumidityRepository.findAll(sort);
		object.put(Constants.AIRHUMIDITY, getLatestData(airHumidities));

		List<AirTemperature> airTemperatures = airTemperatureRepository.findAll(sort);
		object.put(Constants.AIRTEMPERATURE, getLatestData(airTemperatures));
		
		List<co2> co2s = co2Repository.findAll(sort);
		object.put(Constants.CO2, getLatestData(co2s));
		
		List<Illuminance> illuminances = illuminanceRepository.findAll(sort);
		object.put(Constants.ILLUMINANCE, getLatestData(illuminances));
		
		List<SoilHumidity> soilHumidities = soilHumidityRepository.findAll(sort);
		object.put(Constants.SOILHUMIDITY, getLatestData(soilHumidities));
		
		List<SoilTemperature> soilTemperatures = soilTemperatureRepository.findAll(sort);
		object.put(Constants.SOILTEMPERATURE, getLatestData(soilTemperatures));
		
		List<SoilPH> soilPHs = soilPHRepository.findAll(sort);
		object.put(Constants.SOILPH, getLatestData(soilPHs));
		
		return RESCODE.SUCCESS.getJSONRES(object);
	}
	/**
	 * 最新数据数组处理
	 * @param list
	 * @return
	 */
	public Object getLatestData(List list) {		
		if(list.size()>0) {
			BaseDto baseDto = (BaseDto) list.get(list.size()-1);
			Object value = baseDto.getValue();
			logger.debug(baseDto.toString());
			return value;
		}else {
			return null;
		}		
	}
	
	
	public JSONObject getDataInChart(String id,String type) {
		JSONObject jsonObject = new JSONObject();
		switch (type) {
		case Constants.AIRTEMPERATURE:			
			airTemperatureRepository.findById(id);
			break;
		case Constants.AIRHUMIDITY:
			AirHumidity airHumidity = new AirHumidity();
			airHumidity.setDeviceid(id);
			airHumidityRepository.save(airHumidity);
			break;
		case Constants.ILLUMINANCE:
			Illuminance illuminance = new Illuminance();
			illuminance.setDeviceid(id);
			illuminanceRepository.save(illuminance);
			break;
		case Constants.CO2:
			co2 co2 = new co2();
			co2.setDeviceid(id);
			co2Repository.save(co2);
			break;
		case Constants.SOILHUMIDITY:
			SoilHumidity soilHumidity = new SoilHumidity();
			soilHumidity.setDeviceid(id);
			soilHumidityRepository.save(soilHumidity);
			break;
		case Constants.SOILTEMPERATURE:
			SoilTemperature soilTemperature = new SoilTemperature();
			soilTemperature.setDeviceid(id);
			soilTemperatureRepository.save(soilTemperature);
			break;
		case Constants.SOILPH:
			SoilPH soilPH = new SoilPH();
			soilPH.setDeviceid(id);
			soilPHRepository.save(soilPH);
			break;
		default:
			break;
		
		}
		return jsonObject;
	}
	
	
	/**
	 * 更新设备状态
	 * @param id
	 */
	public void updateDevice(String id) {
		logger.debug("进入更新设备状态");
		GetDeviceApi api = new GetDeviceApi(id, api_key);
		BasicResponse<DeviceResponse> response = api.executeApi();
		if(response.errno==0) {
			boolean online = response.data.getIsonline();
			Device device = new Device();
			device.setId(id);
			device.setOnline(online);
			device.setTime(new Date());
			logger.debug("时间："+sdf.format(device.getTime())+",设备："+id+"的(在线/离线)状态为"+online);
			deviceRepository.save(device);
		}else {
			logger.debug(new Date()+"更新设备状态失败");
			logger.debug("失败原因："+response.error);
		}		
	}
	/**
	 * 更新数据
	 * @param id
	 */
	public void updateData(String id) {	
		logger.debug("进入数据更新");
		logger.debug("更新时间为："+sdf.format(new Date()));
		FindDatastreamListApi api = new FindDatastreamListApi(null, id, api_key);
		BasicResponse<List<DatastreamsResponse>> response = api.executeApi();
		JSONObject jsonObject = JSONObject.parseObject(response.json);
		JSONArray data = (JSONArray) jsonObject.get("data");
		for(int i = 0 ; i < data.size() ; i++) {			
			JSONObject object = (JSONObject) data.get(i);
			String did = object.getString("id");
			Date date_update = object.getDate("update_at");
			double current_value = object.getDoubleValue("current_value");
			switch (did) {
			case Constants.AIRTEMPERATURE:
				AirTemperature airTemperature = new AirTemperature();
				airTemperature.setDeviceid(id);
				airTemperature.setTime(date_update);
				airTemperature.setValue(current_value);
				airTemperatureRepository.save(airTemperature);
				break;
			case Constants.AIRHUMIDITY:
				AirHumidity airHumidity = new AirHumidity();
				airHumidity.setDeviceid(id);
				airHumidity.setTime(date_update);
				airHumidity.setValue(current_value);
				airHumidityRepository.save(airHumidity);
				break;
			case Constants.ILLUMINANCE:
				Illuminance illuminance = new Illuminance();
				illuminance.setDeviceid(id);
				illuminance.setTime(date_update);
				illuminance.setValue(current_value);
				illuminanceRepository.save(illuminance);
				break;
			case Constants.CO2:
				co2 co2 = new co2();
				co2.setDeviceid(id);
				co2.setTime(date_update);
				co2.setValue(current_value);
				co2Repository.save(co2);
				break;
			case Constants.SOILHUMIDITY:
				SoilHumidity soilHumidity = new SoilHumidity();
				soilHumidity.setDeviceid(id);
				soilHumidity.setTime(date_update);
				soilHumidity.setValue(current_value);
				soilHumidityRepository.save(soilHumidity);
				break;
			case Constants.SOILTEMPERATURE:
				SoilTemperature soilTemperature = new SoilTemperature();
				soilTemperature.setDeviceid(id);
				soilTemperature.setTime(date_update);
				soilTemperature.setValue(current_value);
				soilTemperatureRepository.save(soilTemperature);
				break;
			case Constants.SOILPH:
				SoilPH soilPH = new SoilPH();
				soilPH.setDeviceid(id);
				soilPH.setTime(date_update);
				soilPH.setValue(current_value);
				soilPHRepository.save(soilPH);
				break;
			default:
				break;
			}
		}
		
	}
}

