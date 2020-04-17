package com.dreamsjewelrystudio.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.hibernate.collection.internal.PersistentBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.Product;
import com.dreamsjewelrystudio.models.ProductImages;
import com.dreamsjewelrystudio.models.ProductPriceSize;
import com.dreamsjewelrystudio.repository.ProductImagesRepository;
import com.dreamsjewelrystudio.repository.ProductPriceSizeRepository;
import com.dreamsjewelrystudio.repository.ProductRepository;

@Service
public class ProductServiceImpl{
	
	@Autowired
	private EntityManagerFactory emf;
	
	@Autowired
	private ProductRepository prdRepository;

	@Autowired
	private ProductImagesRepository pimgRepository;
	
	@Autowired
	private ProductPriceSizeRepository prsRepository;
	
	public Product persistProduct(Product product) {
		return prdRepository.saveAndFlush(product);
	}
	
	public List<Product> findAllWithChildren(){
		return getProductChildren(prdRepository.findAll());
	}
	
	public int selectCountProduct() {
		return prdRepository.selectProductCount();
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getAllProductsWithChildrenLimit(int from, int limit){
		EntityManager em = emf.createEntityManager();
		Query query = em.createNativeQuery("SELECT * FROM product as p " + 
			"INNER JOIN product_price_size as prs ON p.product_id = prs.product_id limit ?1, ?2", Product.class);
		query.setParameter(1, from);
		query.setParameter(2, limit);
		List<Product> list = query.getResultList();
		
		for(Product p : list) {
			List<?> prsList = p.getPrice();
			if(prsList instanceof PersistentBag) {
				PersistentBag pb = (PersistentBag) prsList;
				for(ProductPriceSize prs : (List<ProductPriceSize>) pb.getValue()) {
					if(p.getPrice().contains(prs)) {
						p.getPrice().remove(prs);
					}
				}
			}
		}
		em.close();
		return list;
		
//		List<Product> products = prdRepository.selectProductLimit(from, limit);
//		return getProductChildren(products);
	}
	
	public List<Product> getAllProdcutsByType(String productType, int from, int limit){
		List<Product> products = prdRepository.selectProductsWhereProductType(productType, from, limit);
		return getProductChildren(products);
	}
	
	public List<Product> getProductChildren(List<Product> products) {
		List<Product> productsWithChildren = new ArrayList<>();
		for(Product p : products) {
			if(productsWithChildren.contains(p)) continue;
			Long id = p.getProduct_id();
			List<ProductImages> imgs = pimgRepository.findProductImagesByProductIDNative(id);
			List<ProductPriceSize> priceSize = prsRepository.findProductPriceSizeByProductIDNative(id);
			
			p.setPrice(priceSize);
			p.setImages(imgs);
			productsWithChildren.add(p);
		}
		return productsWithChildren;
	}
	
	public List<ProductPriceSize> getPriceSizeBySize(String size) {
		return prsRepository.findProductPriceSizeBySize(size);
	}
	
	public Product getProductByID(long id, boolean getImages, boolean getPriceSize) {
		Product product = prdRepository.findProductsByID(id);
		List<ProductImages> imgs = null;
		List<ProductPriceSize> priceSize = null;
		
		if(getImages)
			imgs = pimgRepository.findProductImagesByProductIDNative(id);
		if(getPriceSize)
			priceSize = prsRepository.findProductPriceSizeByProductIDNative(id);
		
		product.setPrice(priceSize);
		product.setImages(imgs);
		
		return product;
	}
} 
