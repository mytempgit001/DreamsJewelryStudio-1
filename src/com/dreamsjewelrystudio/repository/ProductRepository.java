package com.dreamsjewelrystudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamsjewelrystudio.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
