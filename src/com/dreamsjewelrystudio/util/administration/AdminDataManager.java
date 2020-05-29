package com.dreamsjewelrystudio.util.administration;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.dreamsjewelrystudio.models.Product;
import com.dreamsjewelrystudio.models.ProductImages;
import com.dreamsjewelrystudio.models.ProductPriceSize;
import com.dreamsjewelrystudio.service.CRUDService;
import com.dreamsjewelrystudio.service.ProductImagesService;
import com.dreamsjewelrystudio.service.ProductPriceSizeService;
import com.dreamsjewelrystudio.service.ProductService;

public class AdminDataManager {
	
	@Autowired private ApplicationContext context;
	@Autowired private ProductService prdSrvc;
	@Autowired private ProductImagesService pimgSrvc;
	@Autowired private ProductPriceSizeService prsSrvc;
	
	public Map<List<String>, List<List<String>>> revealClass(String className) {
		try {
			Class<?> clazz = Class.forName("com.dreamsjewelrystudio.service." + className);
			CRUDService<?> srvc = (CRUDService<?>) context.getBean(clazz);

			Field fields [] = srvc.getModel().getDeclaredFields(); 
			Collection<?> collection = srvc.findAll();
			
			List<String> columns = Stream.of(fields)
					.map(field-> field.getName())
					.collect(Collectors.toList());
			
			List<List<String>> rows = collection.stream()
					.map(element -> {  
						return Stream.of(fields)
								.map(field -> {
									try {
										field.setAccessible(true);
										return field.get(element).toString();
									}catch(Exception e) {
										return "";
									}
								}).collect(Collectors.toList());
					}).collect(Collectors.toList());
			
			return Map.of(columns, rows);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean insertProduct(Product product) {
		List<ProductImages> pimg = product.getImages();
		List<ProductPriceSize> prs = product.getPrice();
		
		String dateTime[] = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()).split(" ");
		product.setDate(dateTime[0]);
		product.setTime(dateTime[1]);
		product.setPrice(null);
		product.setImages(null);
		
		Product prd = prdSrvc.persistProduct(product);
		pimg.parallelStream().forEach(i -> i.setProduct_id(prd.getProduct_id()));
		prs.parallelStream().forEach(i -> i.setProduct_id(prd.getProduct_id()));
		
		pimgSrvc.persistAll(pimg);
		prsSrvc.persistsAll(prs);
		return true;
	}
}
