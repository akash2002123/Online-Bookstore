package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Order;

/**
 * Repository interface for Order entities. 
 * Extends JpaRepository to provide CRUD operations.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

	/**
	 * Finds all orders for a specific user.
	 * 
	 * @param userId the ID of the user
	 * @return a list of orders belonging to the user
	 */
	List<Order> findByUserId(Long userId);
}