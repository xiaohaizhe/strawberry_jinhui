package com.jh.strawberry.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.jh.strawberry.dto.AirTemperature;
import org.springframework.data.mongodb.repository.Query;


/**
 * @author pyt
 * @createTime 2018年12月4日下午5:39:07
 */
public interface AirTemperatureRepository extends MongoRepository<AirTemperature, Long> {
    @Query("{'deviceid':?0}")
    Page<AirTemperature> findByDeviceid(String deviceid, Pageable page);
}

