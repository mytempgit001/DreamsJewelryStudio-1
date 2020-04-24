package com.dreamsjewelrystudio.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.Item;
import com.dreamsjewelrystudio.models.Session;
import com.dreamsjewelrystudio.repository.ItemRepository;

@Service
public class ItemService {
	
	@Autowired private ItemRepository itemRepo;
	@Autowired private EntityManagerFactory emf;
	
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
	
	public List<Item> getItemProductBySession(Session sess) {
		String SQL_SELECT = "SELECT i.*, p.*, prs.*, pimg.* FROM item as i" 
				+ " INNER JOIN product as p ON i.productID = p.product_id"
				+ " INNER JOIN product_price_size as prs ON p.product_id = prs.product_id"
				+ " INNER JOIN product_images as pimg ON p.product_id = pimg.product_id"
				+ " WHERE i.sessID = :id AND i.size = prs.size AND i.price_id = prs.price_id";
		EntityManager em = emf.createEntityManager();
		org.hibernate.Session session =  (org.hibernate.Session) em.getDelegate();
		
		List<?> rows = session.createSQLQuery(SQL_SELECT)
				.addEntity("i", Item.class)
				.addJoin("p", "i.product")
				.addJoin("prs", "p.price")
				.addJoin("pimg", "p.images")
				.setParameter("id", sess.getSessID())
				.getResultList();
		
		session.close();
		List<Item> list = new ArrayList<>();
		for(Object row : rows) {
			if(row instanceof Object[]) {
				Object[] objs = (Object[]) row;
				if(objs[0] instanceof Item) {
					Item i = (Item) objs[0];
					list.add(i);
				}
			}
		}
		return list;
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
