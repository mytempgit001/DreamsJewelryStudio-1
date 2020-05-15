package com.dreamsjewelrystudio.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.Product;
import com.dreamsjewelrystudio.projections.RelatedProductsProjection;
import com.dreamsjewelrystudio.repository.ProductRepository;

@Service
public class ProductService extends CRUDService<Product>{
	
	@Autowired private EntityManagerFactory emf;
	@Autowired private ProductRepository prdRepository;
	
	public List<Product> findAll(){
		return prdRepository.findAll();
	}
	
	public int selectCountProduct() {
		return (int) prdRepository.count();
	}
	
	public Product persistProduct(Product product) {
		return prdRepository.saveAndFlush(product);
	}
	
	public List<Product> findProductsWithPriceLimit(int from, int limit){
		EntityManager em = emf.createEntityManager();
		List<Product> list = em.createQuery("SELECT p FROM Product p", Product.class)
				.setFirstResult(from)
				.setMaxResults(limit)
				.setHint("javax.persistence.fetchgraph", em.createEntityGraph("prd-prs"))
				.getResultList(); 
		em.close();
		return list;
	}
	
	public List<Product> findProductsWithPriceByTypeOrCategoryLimit(String arg, int from, int limit){
		String attribute = "category";
		if(arg.contains("Jewelry")) {
			attribute = "product_type";
			switch(arg) {
				case "Epoxy Resin Jewelry": arg = "Resin"; break;
				case "Gemstone Jewelry": arg = "Gemstone"; break;
			}
		}
		
		EntityManager em = emf.createEntityManager();
		List<Product> list = em.createQuery("SELECT p "
				+ "FROM Product p "
				+ "WHERE p." + attribute + " = :arg", Product.class)
				.setFirstResult(from)
				.setMaxResults(limit)
				.setParameter("arg", arg)
				.setHint("javax.persistence.fetchgraph", em.createEntityGraph("prd-prs"))
				.getResultList();
		em.close();
		return list;
	}
	
	public Product findProductWithChildrenByID(long id) {
		EntityManager em = emf.createEntityManager();
		Product product = em.createQuery("SELECT distinct p "
						+ "FROM Product p "
						+ "WHERE p.product_id = :id", Product.class)
				.setParameter("id", id)
				.setHint("javax.persistence.fetchgraph", em.createEntityGraph("prd-prs"))
				.getSingleResult();
		product  = em.createQuery("SELECT distinct p "
						+ "FROM Product p "
						+ "WHERE p in :products", Product.class)
				.setParameter("products", product)
				.setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
				.setHint("javax.persistence.fetchgraph", em.createEntityGraph("prd-pimg"))
				.getSingleResult();
		
		em.close();
		return product;
	}
	
	public List<RelatedProductsProjection> findRelated(String category, Long id, Pageable pageable){
		return prdRepository.findAll(category, id, pageable);
	}

	@Override
	public void insert(Product entity) {
		
	}

	@Override
	public void delete(Product entity) {
		
	}
} 
