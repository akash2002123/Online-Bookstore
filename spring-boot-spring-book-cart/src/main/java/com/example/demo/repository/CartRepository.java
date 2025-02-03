package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CartItem;

/**
 * Repository interface for CartItem entities. 
 * Extends JpaRepository to provide CRUD operations.
 */
@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {

	/**
	 * Finds all cart items for a specific user.
	 * 
	 * @param userId the ID of the user
	 * @return a list of cart items belonging to the user
	 */
	List<CartItem> findByUserId(Long userId);
}