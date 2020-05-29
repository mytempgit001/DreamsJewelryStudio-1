package com.dreamsjewelrystudio.service;

import java.util.Collection;

public abstract class CRUDService<T> {
	protected Class<T> model;
	
	public CRUDService(Class<T> entityClass) {this.model = entityClass;}
	
	public abstract Collection<?> findAll();
	public abstract void insert(T entity);
	public abstract void delete(T entity);
	
	public Class<?> getModel(){
		return model;
	}
}
