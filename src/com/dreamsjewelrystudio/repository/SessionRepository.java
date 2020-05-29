package com.dreamsjewelrystudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamsjewelrystudio.models.Session;
public interface SessionRepository extends JpaRepository<Session, Long>{
	Session findSessionByToken(String token);
}
