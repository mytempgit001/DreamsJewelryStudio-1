package com.dreamsjewelrystudio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dreamsjewelrystudio.models.ProductPriceSize;

public interface ProductPriceSizeRepository extends JpaRepository<ProductPriceSize, Long>{
	
	@Query(value = "SELECT * FROM product_price_size p WHERE p.product_id = ?1", nativeQuery = true)
	List<ProductPriceSize> findProductPriceSizeByProductIDNative(Long productID);
	
	@Query(value = "SELECT * FROM product_price_size p WHERE p.size = ?1", nativeQuery = true)
	List<ProductPriceSize> findProductPriceSizeBySize(String size);
	
	@Query(value = "SELECT * FROM product_price_size p WHERE p.size = ?1 AND p.product_id = ?2", nativeQuery = true)
	ProductPriceSize findPrsBySizeAndProdId(String size, long id);
}
