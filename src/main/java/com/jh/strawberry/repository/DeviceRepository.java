package com.jh.strawberry.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jh.strawberry.dto.Device;

/**
 * @author pyt
 * @createTime 2018年12月4日下午3:44:18
 */
public interface DeviceRepository extends MongoRepository<Device, String> {
	 public Optional<Device> findById(String id);
}

