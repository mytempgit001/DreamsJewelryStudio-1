package com.dreamsjewelrystudio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.ProductPriceSize;
import com.dreamsjewelrystudio.repository.ProductPriceSizeRepository;

@Service
public class ProductPriceSizeService {

	@Autowired
	private ProductPriceSizeRepository productPriceSizeRepo;
	
	public List<ProductPriceSize> findAll(){
		return productPriceSizeRepo.findAll();
	}
}
