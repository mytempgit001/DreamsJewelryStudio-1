package com.dreamsjewelrystudio.util.administration;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.dreamsjewelrystudio.service.CRUDService;

public class AdminDataManager {
	
	@Autowired private ApplicationContext context;
	
	public Collection<?> revealClass(String className) {
		try {
			Class<?> clazz = Class.forName("com.dreamsjewelrystudio.service." + className);
			CRUDService<?> srvc = (CRUDService<?>) context.getBean(clazz);
			
			Collection<?> collection = srvc.findAll();
			Iterator<?> iterator = collection.iterator();
			
			Field fields [] = srvc.getModel().getDeclaredFields(); 
			Map<Object, Object> map = Stream.of(fields).collect(Collectors.toMap(
					f -> {return f.toString();}, 
					f -> {List<String> list = collection.stream().map(el -> {
						try {
							return f.get(el).toString();
						} catch (Exception e) {
							return "";
						} 
					}).collect(Collectors.toList()); return list;}
				));
			
			System.out.println(map.size());
//			for(Field f : srvc.getModel().getClass().getDeclaredFields()) {
//				collection.stream().map(el -> {
//					try {
//						return f.get(el).toString();
//					} catch (Exception e) {
//						return "";
//					} 
//				}).collect(Collectors.toList());
//			}
			
//			while(iterator.hasNext()) {
//				Field fields [] = iterator.next().getClass().getDeclaredFields();
//				List<String> properties = Stream.of(fields)
//					.map(f -> {
//						f.setAccessible(true);
//						try {
//							return f.get(iterator.next()).toString();
//						}catch(Exception e) {
//							return "";
//						}
//					}).collect(Collectors.toList());
//			}
			return collection;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
