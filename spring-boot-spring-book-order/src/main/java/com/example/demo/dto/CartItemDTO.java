package com.example.demo.dto;

import lombok.Data;

@Data
public class CartItemDTO {
	private Long id;
	private Long userId;
	private Long bookId;
	private int quantity;
	private String bookName;
	private Double price;
}