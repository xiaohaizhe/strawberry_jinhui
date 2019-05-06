package com.jh.strawberry.repository;

import java.util.Optional;

import com.jh.strawberry.dto.co2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.jh.strawberry.dto.AirTemperature;
import com.jh.strawberry.dto.Illuminance;
import org.springframework.data.mongodb.repository.Query;

/**
 * @author pyt
 * @createTime 2018年12月4日下午5:40:43
 */
public interface IlluminanceRepository extends MongoRepository<Illuminance, Long> {
	@Query("{'deviceid':?0}")
	Optional<Illuminance> findByDeviceid(String deviceid);

	@Query("{'deviceid':?0}")
	Page<Illuminance> findByDeviceid(String deviceid, Pageable page);
}

