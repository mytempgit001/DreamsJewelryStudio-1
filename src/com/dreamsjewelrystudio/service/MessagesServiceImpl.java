package com.dreamsjewelrystudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.Messages;
import com.dreamsjewelrystudio.repository.MessagesRepository;

@Service
public class MessagesServiceImpl {
	
	@Autowired
	private MessagesRepository msgRepo;
	
	public void updateMessage(Messages msg) {
		msgRepo.saveAndFlush(msg);
	}
}
