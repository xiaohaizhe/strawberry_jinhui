package com.jh.strawberry.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.jh.strawberry.dto.AirTemperature;

/**
 * @author pyt
 * @createTime 2018年12月4日下午5:39:07
 */
public interface AirTemperatureRepository extends MongoRepository<AirTemperature, String> {
}

