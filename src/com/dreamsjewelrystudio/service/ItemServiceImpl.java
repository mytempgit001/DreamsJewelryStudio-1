package com.dreamsjewelrystudio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.Item;
import com.dreamsjewelrystudio.models.Product;
import com.dreamsjewelrystudio.models.Session;
import com.dreamsjewelrystudio.repository.ItemRepository;

@Service
public class ItemServiceImpl {
	
	@Autowired
	private ItemRepository itemRepo;
	
	@Autowired
	private ProductServiceImpl prdSrvc;
	
	public List<Item> findAll(){
		return itemRepo.findAll();
	}

	public Item updateItem(Item item) {
		return itemRepo.saveAndFlush(item);
	}
	
	public Item persistItem(Item item) {
		return itemRepo.saveAndFlush(item);
	}
	
	public Item getItemWithProductId(long product_id) {
		return itemRepo.findItemByProductId(product_id);
	}
	
	public List<Item> getItemsWithSessionId(long sessID){
		return itemRepo.findItemsBySessionId(sessID);
	}
	
	public List<Item> getItemsAndProductWtihChildrenWithSessionId(long sessID){
		List<Item> items = itemRepo.findItemsBySessionId(sessID);
		List<Product> products = new ArrayList<>();
		items.stream().forEach(item -> products.add(item.getProduct()));
		prdSrvc.getProductChildren(products);
		return items;
	}
	
	public List<Item> getItemWithSession(Session session) {
		return itemRepo.findBySession(session);
	}
	
	public void removeItem(long id) {
		itemRepo.deleteById(id);
	}
	
	public Item findItemById(long itemID) {
		return itemRepo.getOne(itemID);
	}
	
	public void updateQuantity(long itemID, int qty) {
		itemRepo.updateQuantity(itemID, qty);
	}
}
