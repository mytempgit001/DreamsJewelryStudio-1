package com.dreamsjewelrystudio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.ProductPriceSize;
import com.dreamsjewelrystudio.repository.ProductPriceSizeRepository;

@Service
public class ProductPriceSizeService {

	@Autowired
	private ProductPriceSizeRepository prsRepository;
	
	public List<ProductPriceSize> findAll(){
		return prsRepository.findAll();
	}
	
	public ProductPriceSize persistsEntry(ProductPriceSize prs) {
		return prsRepository.saveAndFlush(prs);
	}
	
	public List<ProductPriceSize> persistsAll(List<ProductPriceSize> prs) {
		return prsRepository.saveAll(prs);
	}
}
