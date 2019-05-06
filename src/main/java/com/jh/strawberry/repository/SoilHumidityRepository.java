package com.jh.strawberry.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.jh.strawberry.dto.Illuminance;
import com.jh.strawberry.dto.SoilHumidity;
import org.springframework.data.mongodb.repository.Query;

/**
 * @author pyt
 * @createTime 2018年12月4日下午5:41:30
 */
public interface SoilHumidityRepository extends MongoRepository<SoilHumidity, Long> {
	@Query("{'deviceid':?0}")
	Optional<SoilHumidity> findByDeviceid(String deviceid);

	@Query("{'deviceid':?0}")
	Page<SoilHumidity> findByDeviceid(String deviceid, Pageable page);
}

