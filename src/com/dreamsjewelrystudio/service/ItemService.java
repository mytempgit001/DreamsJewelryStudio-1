package com.dreamsjewelrystudio.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.Item;
import com.dreamsjewelrystudio.repository.ItemRepository;

@Service
public class ItemService extends CRUDService<Item>{
	
	public ItemService() {
		super(Item.class);
	}

	@Autowired private ItemRepository itemRepo;
	@Autowired private EntityManagerFactory emf;
	
	public Item getItemWithProductId(long product_id) {
		return itemRepo.findItemByProductId(product_id);
	}
	
	public void removeItemById(long id) {
		itemRepo.deleteById(id);
	}
	
	public Item findItemById(long itemID) {
		return itemRepo.findById(itemID).orElse(null);
	}
	
	public Item findItemWithPrsById(long itemID){
		EntityManager em = emf.createEntityManager();
		Item item = em.createQuery("SELECT i FROM Item i WHERE i.itemID = :itemID", Item.class)
				.setHint("javax.persistence.fetchgraph", em.createEntityGraph("itm-prs"))
				.setParameter("itemID", itemID)
				.getSingleResult(); 
		em.close();
		return item;
	}
	
	public void updateItemQuantity(int qty, float price, long itemID) {
		itemRepo.updateQuantity(qty, price, itemID);
	}


	public List<Item> findAll(){
		return itemRepo.findAll();
	}

	public void insert(Item item) {
		itemRepo.saveAndFlush(item);
	}
	
	@Override
	public void delete(Item entity) {
		itemRepo.delete(entity);
	}
}
