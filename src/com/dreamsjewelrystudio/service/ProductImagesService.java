package com.dreamsjewelrystudio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.ProductImages;
import com.dreamsjewelrystudio.repository.ProductImagesRepository;

@Service
public class ProductImagesService extends CRUDService<ProductImages>{
	
	@Autowired
	private ProductImagesRepository pimgRepo;
	
	public List<ProductImages> findAll(){
		return pimgRepo.findAll();
	}
	
	public List<ProductImages> persistAll(List<ProductImages> img) {
		return pimgRepo.saveAll(img);
	}

	public void insert(ProductImages img) {
		pimgRepo.saveAndFlush(img);
	}
	
	@Override
	public void delete(ProductImages entity) {
		pimgRepo.delete(entity);
	}
}
