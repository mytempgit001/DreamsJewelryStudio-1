package com.dreamsjewelrystudio.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dreamsjewelrystudio.models.Product;
import com.dreamsjewelrystudio.projections.RelatedProductsProjection;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query("SELECT p from Product p where p.category =?1 AND p.product_id !=?2") 
	List<RelatedProductsProjection> findAll(String category, Long id, Pageable pageable);
	
}
