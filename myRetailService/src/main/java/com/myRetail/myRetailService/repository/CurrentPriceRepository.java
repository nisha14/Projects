package com.myRetail.myRetailService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.myRetail.myRetailService.model.CurrentPrice;

@Repository
public interface CurrentPriceRepository extends MongoRepository<CurrentPrice, String>{
	
}
