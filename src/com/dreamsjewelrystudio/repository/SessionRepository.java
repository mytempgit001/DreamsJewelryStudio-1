package com.dreamsjewelrystudio.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dreamsjewelrystudio.models.Session;
@Transactional
public interface SessionRepository extends JpaRepository<Session, Long>{
	
	@Query(value="SELECT * FROM session WHERE token = ?1", nativeQuery = true)
	Session getSessionByToken(String token);
	
}
