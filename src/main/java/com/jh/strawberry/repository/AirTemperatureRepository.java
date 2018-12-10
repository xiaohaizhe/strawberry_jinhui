package com.jh.strawberry.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.jh.strawberry.dto.AirHumidity;
import com.jh.strawberry.dto.AirTemperature;

/**
 * @author pyt
 * @createTime 2018年12月4日下午5:39:07
 */
public interface AirTemperatureRepository extends MongoRepository<AirTemperature, String> {
}

