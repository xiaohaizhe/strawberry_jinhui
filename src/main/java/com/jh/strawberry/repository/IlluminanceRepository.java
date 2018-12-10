package com.jh.strawberry.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jh.strawberry.dto.AirTemperature;
import com.jh.strawberry.dto.Illuminance;

/**
 * @author pyt
 * @createTime 2018年12月4日下午5:40:43
 */
public interface IlluminanceRepository extends MongoRepository<Illuminance, String> {
	Optional<Illuminance> findByDeviceid(String deviceid);
}

