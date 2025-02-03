package com.example.demo.dto;

import lombok.Data;

/**
 * Data Transfer Object for creating an order.
 */
@Data
public class OrderRequestDTO {

	private Long userId;

	private String address;
}