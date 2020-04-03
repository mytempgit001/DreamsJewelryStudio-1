package com.dreamsjewelrystudio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dreamsjewelrystudio.models.ProductImages;

public interface ProductImagesRepository extends JpaRepository<ProductImages, Long>{
	
	@Query(value = "SELECT * FROM product_images img WHERE img.product_id = ?1", nativeQuery = true)
	List<ProductImages> findProductImagesByProductIDNative(Long productID);
}
