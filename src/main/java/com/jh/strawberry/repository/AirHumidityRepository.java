package com.jh.strawberry.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.jh.strawberry.dto.AirHumidity;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

/**
 * @author pyt
 * @createTime 2018年12月4日下午5:38:27
 */
public interface AirHumidityRepository extends MongoRepository<AirHumidity, Long> {
//    @Query("{'deviceid':?0}")
//    Optional<AirHumidity> findByDeviceid(String deviceid);

    @Query("{'deviceid':?0}")
    Page<AirHumidity> findByDeviceid(String deviceid,Pageable page);
}

