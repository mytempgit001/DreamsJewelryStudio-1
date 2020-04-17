package com.dreamsjewelrystudio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.Admins;
import com.dreamsjewelrystudio.repository.AdminsRepository;

@Service
public class AdminsService {
	
	@Autowired
	private AdminsRepository admRepository;
	
	public void insertAdmin(Admins admin) {
		admRepository.saveAndFlush(admin);
	}
	
	public Admins findAdminByName(String name) {
		return admRepository.findByName(name);
	}
	
	public Admins findAdminBySession(String token) {
		return admRepository.findBySession(token);
	}
	
	public void updateToken(Admins admin) {
		admRepository.saveAndFlush(admin);
	}
	
	public List<Admins> findAll(){
		return admRepository.findAll();
	}
}
