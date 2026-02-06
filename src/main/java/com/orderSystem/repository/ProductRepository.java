package com.orderSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderSystem.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
