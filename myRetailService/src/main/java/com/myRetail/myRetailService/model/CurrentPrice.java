package com.myRetail.myRetailService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class CurrentPrice {
	
	@Id
	@JsonIgnore
	int productId;
	double value;
	@JsonProperty("currency_code")
	String currency;
	
	public CurrentPrice() {
		
	}
	
	public CurrentPrice(int productId, double value, String currency) {
		super();
		this.productId = productId;
		this.value = value;
		this.currency = currency;
	}
	
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
