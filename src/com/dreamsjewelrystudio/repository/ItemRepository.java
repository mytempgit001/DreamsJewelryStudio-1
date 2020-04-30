package com.dreamsjewelrystudio.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dreamsjewelrystudio.models.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
	
	@Query(value="SELECT * FROM item WHERE productID = ?1", nativeQuery = true)
	Item findItemByProductId(long id);

	@Transactional
	@Modifying
	@Query(value="UPDATE item SET quantity = ?1, price = ?2 WHERE itemID = ?3", nativeQuery = true)
	void updateQuantity(int qty, float price, long id);
	
}
