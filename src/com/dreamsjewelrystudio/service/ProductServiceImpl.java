package com.dreamsjewelrystudio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.Product;
import com.dreamsjewelrystudio.models.ProductImages;
import com.dreamsjewelrystudio.models.ProductPriceSize;
import com.dreamsjewelrystudio.repository.ProductImagesRepository;
import com.dreamsjewelrystudio.repository.ProductPriceSizeRepository;
import com.dreamsjewelrystudio.repository.ProductRepository;

@Service
public class ProductServiceImpl{
	
	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private ProductImagesRepository productImageRepo;
	
	@Autowired
	private ProductPriceSizeRepository productPriceSizeRepo;
	
	public List<Product> getAll(){
		return productRepo.findAll();
	}
	
	public int selectCountProduct() {
		return productRepo.selectProductCount();
	}
	
	public List<Product> getAllProducts(int from, int limit){
		List<Product> products = productRepo.selectProductLimit(from, limit);
		return getProductChildren(products);
	}
	
	public List<Product> getAllProdcutsByType(String productType, int from, int limit){
		List<Product> products = productRepo.selectProductsWhereProductType(productType, from, limit);
		return getProductChildren(products);
	}
	
	public List<Product> getProductChildren(List<Product> products) {
		List<Product> productsWithChildren = new ArrayList<>();
		for(Product p : products) {
			if(productsWithChildren.contains(p)) continue;
			Long id = p.getProduct_id();
			List<ProductImages> imgs = productImageRepo.findProductImagesByProductIDNative(id);
			List<ProductPriceSize> priceSize = productPriceSizeRepo.findProductPriceSizeByProductIDNative(id);
			
			p.setPrice(priceSize);
			p.setImages(imgs);
			productsWithChildren.add(p);
		}
		return productsWithChildren;
	}
	
	public List<ProductPriceSize> getPriceSizeBySize(String size) {
		return productPriceSizeRepo.findProductPriceSizeBySize(size);
	}
	
	public Product getProductByID(long id, boolean getImages, boolean getPriceSize) {
		Product product = productRepo.findProductsByID(id);
		List<ProductImages> imgs = null;
		List<ProductPriceSize> priceSize = null;
		
		if(getImages)
			imgs = productImageRepo.findProductImagesByProductIDNative(id);
		if(getPriceSize)
			priceSize = productPriceSizeRepo.findProductPriceSizeByProductIDNative(id);
		
		product.setPrice(priceSize);
		product.setImages(imgs);
		
		return product;
	}
} 
