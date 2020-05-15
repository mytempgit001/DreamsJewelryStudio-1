package com.dreamsjewelrystudio.service;

import java.util.Collection;

public abstract class CRUDService<T> {
	
	public abstract Collection<?> findAll();
	public abstract void insert(T entity);
	public abstract void delete(T entity);
	
	public Class<?> getModel(){
		return <T>;
	}
}
