package com.myRetail.myRetailService.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myRetail.myRetailService.model.CurrentPrice;
import com.myRetail.myRetailService.repository.CurrentPriceRepository;

@Service
public class CurrentPriceService {

	@Autowired
	private CurrentPriceRepository priceRepository;
	
	public Optional<CurrentPrice> findById(int id) {
		return priceRepository.findById(String.valueOf(id));
	}
	
	public CurrentPrice update(int id, double value, String currency) {
		Optional<CurrentPrice> price = findById(id);
		if(price.isPresent()) {
			price.get().setValue(value);
			price.get().setCurrency(currency);
			return priceRepository.save(price.get());
		} else {
			return priceRepository.save(new CurrentPrice(id, value, currency));
		}
	}
}
