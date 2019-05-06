package com.jh.strawberry.repository;

import java.util.Optional;

import com.jh.strawberry.dto.SoilHumidity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.jh.strawberry.dto.SoilPH;
import com.jh.strawberry.dto.SoilTemperature;
import org.springframework.data.mongodb.repository.Query;

/**
 * @author pyt
 * @createTime 2018年12月4日下午5:42:09
 */
public interface SoilPHRepository extends MongoRepository<SoilPH, Long> {
	@Query("{'deviceid':?0}")
	Optional<SoilPH> findByDeviceid(String deviceid);

	@Query("{'deviceid':?0}")
	Page<SoilPH> findByDeviceid(String deviceid, Pageable page);
}

