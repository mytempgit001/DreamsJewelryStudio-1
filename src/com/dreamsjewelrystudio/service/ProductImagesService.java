package com.dreamsjewelrystudio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.ProductImages;
import com.dreamsjewelrystudio.repository.ProductImagesRepository;

@Service
public class ProductImagesService {
	
	@Autowired
	private ProductImagesRepository prodImagesRepo;
	
	public List<ProductImages> findAll(){
		return prodImagesRepo.findAll();
	}
}
