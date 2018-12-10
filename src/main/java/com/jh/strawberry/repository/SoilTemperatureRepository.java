package com.jh.strawberry.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jh.strawberry.dto.SoilHumidity;
import com.jh.strawberry.dto.SoilTemperature;

/**
 * @author pyt
 * @createTime 2018年12月4日下午5:42:49
 */
public interface SoilTemperatureRepository extends MongoRepository<SoilTemperature, String> {
	Optional<SoilTemperature> findByDeviceid(String deviceid);
}

