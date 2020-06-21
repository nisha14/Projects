package com.myRetail.myRetailService.model;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
	@Id
	int id;
	String name;
	@JsonProperty("current_price")
	CurrentPrice price;
	
	public Product(){
		
	}

	public Product(int id, String name, CurrentPrice price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CurrentPrice getPrice() {
		return price;
	}

	public void setPrice(CurrentPrice price) {
		this.price = price;
	}
}
