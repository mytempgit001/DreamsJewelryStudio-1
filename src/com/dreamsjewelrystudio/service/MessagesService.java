package com.dreamsjewelrystudio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.Messages;
import com.dreamsjewelrystudio.repository.MessagesRepository;

@Service
public class MessagesService {
	
	@Autowired
	private MessagesRepository msgRepo;
	
	public void updateMessage(Messages msg) {
		msgRepo.saveAndFlush(msg);
	}
	
	public List<Messages> findAll(){
		return msgRepo.findAll();
	}
}
