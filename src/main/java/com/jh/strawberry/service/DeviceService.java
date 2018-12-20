package com.jh.strawberry.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.jh.strawberry.utils.MongoDBUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import cmcc.iot.onenet.javasdk.api.datapoints.GetDatapointsListApi;
import cmcc.iot.onenet.javasdk.api.datastreams.FindDatastreamListApi;
import cmcc.iot.onenet.javasdk.api.device.GetDeviceApi;
import cmcc.iot.onenet.javasdk.response.BasicResponse;
import cmcc.iot.onenet.javasdk.response.datapoints.DatapointsList;
import cmcc.iot.onenet.javasdk.response.datapoints.DatapointsList.DatastreamsItem;
import cmcc.iot.onenet.javasdk.response.datapoints.DatapointsList.DatastreamsItem.DatapointsItem;
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
	private static SimpleDateFormat sdfd = new SimpleDateFormat("HH:mm");
	private static SimpleDateFormat sdf_update_0 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdf_update_1 = new SimpleDateFormat("HH:mm:ss");
	
	private static int data_points = 30;
	
	private static MongoDBUtils mongoDBUtil = MongoDBUtils.getInstance();
	private static MongoClient meiyaClient = mongoDBUtil.getMongoConnect("127.0.0.1",27017);
	private static MongoCollection<Document> airHumidity = mongoDBUtil.getMongoCollection(meiyaClient,"jh_strawberry","airHumidity");
	private static MongoCollection<Document> airTemperature = mongoDBUtil.getMongoCollection(meiyaClient,"jh_strawberry","airTemperature");
	private static MongoCollection<Document> co2 = mongoDBUtil.getMongoCollection(meiyaClient,"jh_strawberry","co2");
	private static MongoCollection<Document> illuminance = mongoDBUtil.getMongoCollection(meiyaClient,"jh_strawberry","illuminance");
	private static MongoCollection<Document> soilHumidity = mongoDBUtil.getMongoCollection(meiyaClient,"jh_strawberry","soilHumidity");
	private static MongoCollection<Document> soilTemperature = mongoDBUtil.getMongoCollection(meiyaClient,"jh_strawberry","soilTemperature");
	private static MongoCollection<Document> soilPH = mongoDBUtil.getMongoCollection(meiyaClient,"jh_strawberry","soilPH");
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
		BasicDBObject query = new BasicDBObject();
        query.put("deviceid",id);
        //空气湿度
		FindIterable<Document> documents1 = airHumidity.find(query).sort(new  BasicDBObject("time",-1)).limit(1);
		for (Document d : documents1) {
			object.put(Constants.AIRHUMIDITY, d.get("value"));
		}
		//空气温度
		FindIterable<Document> documents2 = airTemperature.find(query).sort(new  BasicDBObject("time",-1)).limit(1);
		for (Document d : documents2) {
			object.put(Constants.AIRTEMPERATURE, d.get("value"));
		}
		//二氧化碳浓度
		FindIterable<Document> documents3 = co2.find(query).sort(new  BasicDBObject("time",-1)).limit(1);
		for (Document d : documents3) {
			object.put(Constants.CO2, d.get("value"));
		}
		//光照强度
		FindIterable<Document> documents4 = illuminance.find(query).sort(new  BasicDBObject("time",-1)).limit(1);
		for (Document d : documents4) {
			object.put(Constants.ILLUMINANCE, d.get("value"));
		}
		//土壤湿度
		FindIterable<Document> documents5 = soilHumidity.find(query).sort(new  BasicDBObject("time",-1)).limit(1);
		for (Document d : documents5) {
			object.put(Constants.SOILHUMIDITY, d.get("value"));
		}
		//土壤温度
		FindIterable<Document> documents6 = soilTemperature.find(query).sort(new  BasicDBObject("time",-1)).limit(1);
		for (Document d : documents6) {
			object.put(Constants.SOILTEMPERATURE, d.get("value"));
		}
		//土壤酸碱值
		FindIterable<Document> documents7 = soilPH.find(query).sort(new  BasicDBObject("time",-1)).limit(1);
		for (Document d : documents7) {
			object.put(Constants.SOILPH, d.get("value"));
		}
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
	
	/**
	 * 图表数据点：取30个点
	 * @param id
	 * @param type
	 * @return
	 */
	public JSONObject getDataInChart(String id,String type) {
		logger.debug("进入获取图表数据");
		JSONArray data = new JSONArray();
		JSONArray time = new JSONArray();
		BasicDBObject query = new BasicDBObject();
        query.put("deviceid",id);	
        double value = 0;
        int count = 0;
		switch (type) {
		case Constants.AIRTEMPERATURE:			
			//空气温度
			FindIterable<Document> documents2 = airTemperature.find(query).sort(new  BasicDBObject("time",-1)).limit(data_points);
			for (Document d : documents2) {
				Date date = d.getDate("time");
				data.add(d.get("value"));
				value+=(double)d.get("value");
				count++;
				time.add(sdfd.format(date));			
			}
			break;
		case Constants.AIRHUMIDITY:
			//空气湿度
			FindIterable<Document> documents1 = airHumidity.find(query).sort(new  BasicDBObject("time",-1)).limit(data_points);
			for (Document d : documents1) {
				Date date = d.getDate("time");
				data.add(d.get("value"));
				value+=(double)d.get("value");
				count++;
				time.add(sdfd.format(date));
			}
			break;
		case Constants.ILLUMINANCE:
			//光照强度
			FindIterable<Document> documents4 = illuminance.find(query).sort(new  BasicDBObject("time",-1)).limit(data_points);
			for (Document d : documents4) {
				Date date = d.getDate("time");
				data.add(d.get("value"));
				value+=(double)d.get("value");
				count++;
				time.add(sdfd.format(date));
			}
			break;
		case Constants.CO2:
			//二氧化碳浓度
			FindIterable<Document> documents3 = co2.find(query).sort(new  BasicDBObject("time",-1)).limit(data_points);
			for (Document d : documents3) {
				Date date = d.getDate("time");
				data.add(d.get("value"));
				value+=(double)d.get("value");
				count++;
				time.add(sdfd.format(date));
			}
			break;
		case Constants.SOILHUMIDITY:
			//土壤湿度
			FindIterable<Document> documents5 = soilHumidity.find(query).sort(new  BasicDBObject("time",-1)).limit(data_points);
			for (Document d : documents5) {
				Date date = d.getDate("time");
				data.add(d.get("value"));
				value+=(double)d.get("value");
				count++;
				time.add(sdfd.format(date));
			}
			break;
		case Constants.SOILTEMPERATURE:
			//土壤温度
			FindIterable<Document> documents6 = soilTemperature.find(query).sort(new  BasicDBObject("time",-1)).limit(data_points);
			for (Document d : documents6) {
				Date date = d.getDate("time");
				data.add(d.get("value"));
				value+=(double)d.get("value");
				count++;
				time.add(sdfd.format(date));
			}
			break;
		case Constants.SOILPH:
			//土壤酸碱值
			FindIterable<Document> documents7 = soilPH.find(query).sort(new  BasicDBObject("time",-1)).limit(data_points);
			for (Document d : documents7) {
				Date date = d.getDate("time");
				data.add(d.get("value"));
				value+=(double)d.get("value");
				count++;
				time.add(sdfd.format(date));
			}
			break;
		default:
			break;
		
		}
		Collections.reverse(data);
		Collections.reverse(time);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", data);
		jsonObject.put("time", time);
		if(count==0) {
			jsonObject.put("mean", 0);
		}else {
			jsonObject.put("mean", (double)Math.round(value/count*100)/100);
		}		
		return RESCODE.SUCCESS.getJSONRES(jsonObject);
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
	
	public void updateData(String id,Date start,Date end) throws ParseException {
		logger.debug("开始更新数据");
		String dataFormatStart = sdf_update_0.format(start)+"T"+sdf_update_1.format(start);
		String dataFormatEnd  = sdf_update_0.format(end)+"T"+sdf_update_1.format(end);
		logger.debug("开始时间："+dataFormatStart);
		logger.debug("结束时间："+dataFormatEnd);
		GetDatapointsListApi api = new GetDatapointsListApi(null, dataFormatStart, dataFormatEnd, id, null, null, null, null,
				null, null, null, api_key);
		BasicResponse<DatapointsList> response = api.executeApi();
		Map<String, Object> map = null;
		if(response.errno==0) {
			List<DatastreamsItem> dl= response.getData().getDevices();
			logger.debug("参数个数："+dl.size());		
			logger.debug("总共获得数据量为："+response.getData().getCount());
			for(int i=0;i<dl.size();i++) {				
				DatastreamsItem di = dl.get(i);
				List<DatapointsItem> ld =di.getDatapoints();
				logger.debug(di.getId()+"参数下数据量："+ld.size());
				switch (di.getId()) {
				case Constants.AIRTEMPERATURE:			
					//空气温度
					for(int j=0;j<ld.size();j++) {
						AirTemperature airTemperature = new AirTemperature();
						airTemperature.setDeviceid(id);
						airTemperature.setTime(sdf.parse(ld.get(j).getAt()));
						airTemperature.setValue(Double.parseDouble((String)ld.get(j).getValue()));
						airTemperatureRepository.save(airTemperature);
					}
					break;
				case Constants.AIRHUMIDITY:
					//空气湿度
					for(int j=0;j<ld.size();j++) {
						AirHumidity airHumidity = new AirHumidity();
						airHumidity.setDeviceid(id);
						airHumidity.setTime(sdf.parse(ld.get(j).getAt()));
						airHumidity.setValue(Double.parseDouble((String)ld.get(j).getValue()));
						airHumidityRepository.save(airHumidity);
					}
					break;
				case Constants.ILLUMINANCE:
					//光照强度
					for(int j=0;j<ld.size();j++) {
						Illuminance illuminance = new Illuminance();
						illuminance.setDeviceid(id);
						illuminance.setTime(sdf.parse(ld.get(j).getAt()));
						illuminance.setValue(Double.parseDouble((String)ld.get(j).getValue()));
						illuminanceRepository.save(illuminance);
					}
					break;
				case Constants.CO2:
					//二氧化碳浓度
					for(int j=0;j<ld.size();j++) {
						co2 co2 = new co2();
						co2.setDeviceid(id);
						co2.setTime(sdf.parse(ld.get(j).getAt()));
						co2.setValue(Double.parseDouble((String)ld.get(j).getValue()));
						co2Repository.save(co2);
					}
					break;
				case Constants.SOILHUMIDITY:
					//土壤湿度
					for(int j=0;j<ld.size();j++) {
						SoilHumidity soilHumidity = new SoilHumidity();
						soilHumidity.setDeviceid(id);
						soilHumidity.setTime(sdf.parse(ld.get(j).getAt()));
						soilHumidity.setValue(Double.parseDouble((String)ld.get(j).getValue()));
						soilHumidityRepository.save(soilHumidity);
					}
					break;
				case Constants.SOILTEMPERATURE:
					//土壤温度
					for(int j=0;j<ld.size();j++) {
						SoilTemperature soilTemperature = new SoilTemperature();
						soilTemperature.setDeviceid(id);
						soilTemperature.setTime(sdf.parse(ld.get(j).getAt()));
						soilTemperature.setValue(Double.parseDouble((String)ld.get(j).getValue()));
						soilTemperatureRepository.save(soilTemperature);
					}
					break;
				case Constants.SOILPH:
					//土壤酸碱值
					for(int j=0;j<ld.size();j++) {
						SoilPH soilPH = new SoilPH();
						soilPH.setDeviceid(id);
						soilPH.setTime(sdf.parse(ld.get(j).getAt()));
						soilPH.setValue(Double.parseDouble((String)ld.get(j).getValue()));
						soilPHRepository.save(soilPH);
					}
					break;
				default:
					break;
				}				
			}
			logger.debug("数据存储结束！");
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

