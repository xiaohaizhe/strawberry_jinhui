package com.jh.strawberry.repository;

import java.util.Optional;

import com.jh.strawberry.dto.SoilPH;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.jh.strawberry.dto.SoilHumidity;
import com.jh.strawberry.dto.SoilTemperature;
import org.springframework.data.mongodb.repository.Query;

/**
 * @author pyt
 * @createTime 2018年12月4日下午5:42:49
 */
public interface SoilTemperatureRepository extends MongoRepository<SoilTemperature, Long> {
	@Query("{'deviceid':?0}")
	Optional<SoilTemperature> findByDeviceid(String deviceid);

	@Query("{'deviceid':?0}")
	Page<SoilTemperature> findByDeviceid(String deviceid, Pageable page);
}

