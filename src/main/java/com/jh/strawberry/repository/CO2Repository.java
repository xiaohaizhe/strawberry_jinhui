package com.jh.strawberry.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jh.strawberry.dto.AirTemperature;
import com.jh.strawberry.dto.co2;

/**
 * @author pyt
 * @createTime 2018年12月4日下午5:39:51
 */
public interface CO2Repository extends MongoRepository<co2, String> {
	Optional<co2> findByDeviceid(String deviceid);
}

