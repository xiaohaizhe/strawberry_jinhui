package com.jh.strawberry.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jh.strawberry.dto.SoilPH;
import com.jh.strawberry.dto.SoilTemperature;

/**
 * @author pyt
 * @createTime 2018年12月4日下午5:42:09
 */
public interface SoilPHRepository extends MongoRepository<SoilPH, String> {
	Optional<SoilPH> findByDeviceid(String deviceid);
}

