package com.dreamsjewelrystudio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.ProductImages;
import com.dreamsjewelrystudio.repository.ProductImagesRepository;

@Service
public class ProductImagesService {
	
	@Autowired
	private ProductImagesRepository pimgRepo;
	
	public List<ProductImages> findAll(){
		return pimgRepo.findAll();
	}
	
	public ProductImages persistImg(ProductImages img) {
		return pimgRepo.saveAndFlush(img);
	}
	
	public List<ProductImages> persistAll(List<ProductImages> img) {
		return pimgRepo.saveAll(img);
	}
}
