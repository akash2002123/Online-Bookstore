package com.example.demo.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Entity class representing an order. Maps to a database ORDER table using JPA
 * annotations.
 */
@Entity
@Data
@Table(name = "`order`")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@NotNull(message = "User ID cannot be null")
	private Long userId;

	@ElementCollection
	@NotNull(message = "Book IDs cannot be null")
	private List<Long> bookIds;

	@Positive(message = "Total amount must be positive")
	private Double totalAmount;

	@NotBlank(message = "Address cannot be blank")
	private String address;

	@NotBlank(message = "Payment status cannot be blank")
	private String paymentStatus;

	//@NotNull(message = "Delivery date cannot be null")
	private LocalDate deliveryDate;

}