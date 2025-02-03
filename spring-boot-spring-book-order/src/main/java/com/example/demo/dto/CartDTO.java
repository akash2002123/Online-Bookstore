package com.example.demo.dto;

import lombok.Data;
import java.util.List;

/**
 * Data Transfer Object for transferring cart data.
 */
@Data
public class CartDTO {

	private Long userId;
	private List<CartItemDTO> items;

	/**
	 * Data Transfer Object for transferring individual cart item data.
	 */
	@Data
	public static class CartItemDTO {
		private Long bookId;

		private int quantity;

		private double price;

		private String bookName;
	}
}