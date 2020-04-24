package com.dreamsjewelrystudio.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamsjewelrystudio.models.Session;
@Transactional
public interface SessionRepository extends JpaRepository<Session, Long>{
	
	@EntityGraph("session-items")
	Session getSessionByToken(String token);
	
}
