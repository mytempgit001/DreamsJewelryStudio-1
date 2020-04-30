package com.dreamsjewelrystudio.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.Product;
import com.dreamsjewelrystudio.repository.ProductRepository;

@Service
public class ProductService{
	
	@Autowired private EntityManagerFactory emf;
	@Autowired private ProductRepository prdRepository;
	
	public long selectCountProduct() {
		return prdRepository.count();
	}
	
	public Product persistProduct(Product product) {
		return prdRepository.saveAndFlush(product);
	}
	
	public List<Product> findAllWithChildren(){
		EntityManager em = emf.createEntityManager();
		List<Product> products = em.createQuery("SELECT distinct p "
						+ "FROM Product p ", Product.class)
				.setHint("javax.persistence.fetchgraph", em.createEntityGraph("prd-prs"))
				.getResultList();
		products  = em.createQuery("SELECT distinct p "
						+ "FROM Product p "
						+ "WHERE p in :products", Product.class)
				.setParameter("products", products)
				.setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
				.setHint("javax.persistence.fetchgraph", em.createEntityGraph("prd-pimg"))
				.getResultList();
		em.close();
		return products;
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
	
	public List<Product> findProdcutsWithPriceByTypeOrCategoryLimit(String arg, int from, int limit){
		String attribute = "category";
		if(arg.toLowerCase().contains("resin") || arg.toLowerCase().contains("gemstone")) 
			attribute = "product_type";
		
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
	
	public List<Product> findProductsWithChildrenInRange(List<Product> prds) {
		EntityManager em = emf.createEntityManager();
		prds = em.createQuery("SELECT distinct p "
						+ "FROM Product p "
						+ "WHERE p in :prds", Product.class)
				.setParameter("prds", prds)
				.setHint("javax.persistence.fetchgraph", em.createEntityGraph("prd-prs"))
				.getResultList();
		prds  = em.createQuery("SELECT distinct p "
						+ "FROM Product p "
						+ "WHERE p in :products", Product.class)
				.setParameter("products", prds)
				.setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
				.setHint("javax.persistence.fetchgraph", em.createEntityGraph("prd-pimg"))
				.getResultList();
		
		em.close();
		return prds;
	}
} 
