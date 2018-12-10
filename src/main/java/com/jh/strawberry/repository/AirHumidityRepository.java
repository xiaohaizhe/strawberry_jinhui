package com.jh.strawberry.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.jh.strawberry.dto.AirHumidity;

/**
 * @author pyt
 * @createTime 2018年12月4日下午5:38:27
 */
public interface AirHumidityRepository extends MongoRepository<AirHumidity, String> {
}

