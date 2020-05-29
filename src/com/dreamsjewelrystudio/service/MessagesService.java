package com.dreamsjewelrystudio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.Messages;
import com.dreamsjewelrystudio.repository.MessagesRepository;

@Service
public class MessagesService extends CRUDService<Messages>{
	
	public MessagesService() {
		super(Messages.class);
	}

	@Autowired
	private MessagesRepository msgRepo;
	
	public void insert(Messages msg) {
		msgRepo.saveAndFlush(msg);
	}
	
	public List<Messages> findAll(){
		return msgRepo.findAll();
	}

	@Override
	public void delete(Messages entity) {
		msgRepo.delete(entity);
	}
}
