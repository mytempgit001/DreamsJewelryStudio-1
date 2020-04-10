package com.dreamsjewelrystudio.models;

import java.util.List;

public class TestJson {
	private List<ProductImages> images;
	private List<ProductPriceSize> price;
	private String name;
	private Integer myInt;
	private List<String> list;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMyInt() {
		return myInt;
	}
	public void setMyInt(Integer myInt) {
		this.myInt = myInt;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public List<ProductImages> getImages() {
		return images;
	}
	public void setImages(List<ProductImages> images) {
		this.images = images;
	}
	public List<ProductPriceSize> getPrice() {
		return price;
	}
	public void setPrice(List<ProductPriceSize> price) {
		this.price = price;
	}
}
