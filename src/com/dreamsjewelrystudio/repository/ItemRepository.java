package com.dreamsjewelrystudio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dreamsjewelrystudio.models.Item;
import com.dreamsjewelrystudio.models.Session;

public interface ItemRepository extends JpaRepository<Item, Long>{
	
	@Query(value="SELECT * FROM item WHERE productID = ?1", nativeQuery = true)
	Item findItemByProductId(long id);

	@Query(value="SELECT * FROM item WHERE sessID = ?1", nativeQuery = true)
	List<Item> findItemsBySessionId(long id);
	
	List<Item> findBySession(Session session);
	@Query(value="UPDATE item SET quantity = ?2 WHERE itemID = ?1", nativeQuery = true)
	void updateQuantity(long id, int qty);
	
}
