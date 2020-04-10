package com.dreamsjewelrystudio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreamsjewelrystudio.models.Session;
import com.dreamsjewelrystudio.repository.SessionRepository;

@Service
public class SessionServiceImpl {
	
	@Autowired
	private SessionRepository sessRepo;
	
	public Session findSessionByToken(String token) {
		return sessRepo.getSessionByToken(token);
	}
	
	public Session createNewSession(Session session) {
		return sessRepo.saveAndFlush(session);
	}
	
	public List<Session> findAll(){
		return sessRepo.findAll();
	}
}
