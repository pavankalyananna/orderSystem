package com.orderSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderSystem.entity.Order;
public interface OrderRepository extends JpaRepository<Order, Long> {

// Indexed column → fast
List<Order> findByUserId(Long userId);
}
