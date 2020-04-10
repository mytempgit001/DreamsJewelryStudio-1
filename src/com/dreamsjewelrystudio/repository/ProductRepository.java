package com.dreamsjewelrystudio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dreamsjewelrystudio.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query(value="SELECT COUNT(*) FROM product", nativeQuery=true)
	int selectProductCount();
	
	@Query(value="SELECT *\r\n" + 
			"	FROM product as p\r\n" + 
			"	INNER JOIN product_price_size ON p.product_id = product_price_size.product_id\r\n" + 
			"    INNER JOIN product_images ON p.product_id = product_images.product_id\r\n" + 
			"    limit ?1, ?2", nativeQuery=true)
	List<Product> selectProductLimit(int from, int limit);
	
	@Query(value="SELECT * FROM product WHERE product_type = ?1 limit ?2, ?3", nativeQuery = true)
	List<Product> selectProductsWhereProductType(String productType, int from, int limit);
	
	@Query(value="SELECT * FROM product WHERE product_id = ?1", nativeQuery = true)
	Product findProductsByID(long id);
}
