package com.jh.strawberry.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jh.strawberry.dto.Illuminance;
import com.jh.strawberry.dto.SoilHumidity;

/**
 * @author pyt
 * @createTime 2018年12月4日下午5:41:30
 */
public interface SoilHumidityRepository extends MongoRepository<SoilHumidity, String> {
	Optional<SoilHumidity> findByDeviceid(String deviceid);
}

