package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Entity class representing an item in the shopping cart. 
 * Maps to a database table using JPA annotations.
 */
@Entity
@Data
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "User ID cannot be null")
	private Long userId;

	@NotNull(message = "Book ID cannot be null")
	private Long bookId;

	@Min(value = 1, message = "Quantity must be at least 1")
	private int quantity;

	@Positive(message = "Price must be positive")
	private double price;

	@Transient
	private String bookName;

}