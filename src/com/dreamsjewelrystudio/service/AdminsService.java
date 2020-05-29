package com.dreamsjewelrystudio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.Admins;
import com.dreamsjewelrystudio.repository.AdminsRepository;

@Service
public class AdminsService extends CRUDService<Admins>{
	
	public AdminsService() {
		super(Admins.class);
	}

	@Autowired
	private AdminsRepository admRepository;
	
	public Admins findAdminByName(String name) {
		return admRepository.findByName(name);
	}
	
	public Admins findAdminBySession(String token) {
		return admRepository.findBySession(token);
	}
	
	public void updateToken(Admins admin) {
		admRepository.saveAndFlush(admin);
	}
	
	@Override
	public List<Admins> findAll(){
		return admRepository.findAll();
	}
	
	@Override
	public void insert(Admins admin) {
		admRepository.saveAndFlush(admin);
	}

	@Override
	public void delete(Admins admin) {
		admRepository.delete(admin);
	}
}
