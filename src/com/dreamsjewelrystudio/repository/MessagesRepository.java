package com.dreamsjewelrystudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamsjewelrystudio.models.Messages;

public interface MessagesRepository extends JpaRepository<Messages, Long>{

}
