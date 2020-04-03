package com.dreamsjewelrystudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dreamsjewelrystudio.models.Admins;

public interface AdminsRepository extends JpaRepository<Admins, Integer>{
	
	@Query(value="SELECT * FROM admins WHERE name=?1", nativeQuery=true)
	Admins findByName(String name);

	@Query(value="SELECT * FROM admins WHERE token=?1", nativeQuery=true)
	Admins findBySession(String token);
	
	@Query(value="UPDATE admins SET token = ?2 WHERE id = ?1", nativeQuery = true)
	void updateToken(Integer id, String token);
}
