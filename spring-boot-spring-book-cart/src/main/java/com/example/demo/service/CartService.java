package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.CartItem;

/**
 * Service interface for managing cart items.
 */
public interface CartService {

	/**
	 * Retrieves all cart items for a user.
	 * 
	 * @param userId the ID of the user
	 * @return list of cart items
	 */
	List<CartItem> getCartItems(Long userId);

	/**
	 * Adds a new item to the cart.
	 * 
	 * @param cartItem the item to add
	 * @return the added cart item
	 */
	CartItem addCartItem(CartItem cartItem);

	/**
	 * Removes an item from the cart.
	 * 
	 * @param id the ID of the item to remove
	 */
	void removeCartItem(Long id);

	/**
	 * Clears all items from the cart for a user.
	 * 
	 * @param userId the ID of the user
	 */
	void clearCart(Long userId);
}