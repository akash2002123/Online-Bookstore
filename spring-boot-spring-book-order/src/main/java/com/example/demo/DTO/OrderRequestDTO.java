package com.example.demo.DTO;

import lombok.Data;

/**
 * Data Transfer Object for creating an order.
 */
@Data
public class OrderRequestDTO {

	private Long userId;

	private String address;
}