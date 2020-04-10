package com.dreamsjewelrystudio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.Admins;
import com.dreamsjewelrystudio.repository.AdminsRepository;

@Service
public class AdminsService {
	
	@Autowired
	private AdminsRepository adminsRepo;
	
	public void insertAdmin(Admins admin) {
		adminsRepo.saveAndFlush(admin);
	}
	
	public Admins findAdminByName(String name) {
		return adminsRepo.findByName(name);
	}
	
	public Admins findAdminBySession(String token) {
		return adminsRepo.findBySession(token);
	}
	
	public void updateToken(Admins admin) {
		adminsRepo.saveAndFlush(admin);
	}
	
	public List<Admins> findAll(){
		return adminsRepo.findAll();
	}
}
