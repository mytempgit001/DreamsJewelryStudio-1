package com.dreamsjewelrystudio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.ProductPriceSize;
import com.dreamsjewelrystudio.repository.ProductPriceSizeRepository;

@Service
public class ProductPriceSizeService extends CRUDService<ProductPriceSize>{

	public ProductPriceSizeService() {
		super(ProductPriceSize.class);
	}


	@Autowired
	private ProductPriceSizeRepository prsRepository;
	
	public List<ProductPriceSize> findAll(){
		return prsRepository.findAll();
	}
	
	public void insert(ProductPriceSize prs) {
		prsRepository.saveAndFlush(prs);
	}
	
	public List<ProductPriceSize> persistsAll(List<ProductPriceSize> prs) {
		return prsRepository.saveAll(prs);
	}
	
	public ProductPriceSize getPrsBySizeAndProductId(String size, long id) {
		return prsRepository.findPrsBySizeAndProdId(size, id);
	}


	@Override
	public void delete(ProductPriceSize entity) {
		prsRepository.delete(entity);
	}
}
