package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CartItem;
import com.example.demo.service.CartService;

/**
 * REST controller for managing the shopping cart. Provides endpoints for
 * adding, retrieving, and removing cart items.
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

	private final CartService cartService;

	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	// Removed JWT validation for simplicity

	/**
	 * GET /api/cart/{userId} : Get all cart items for a specific user.
	 * 
	 * @param userId the id of the user
	 * @return the list of cart items
	 */
	@GetMapping("/{userId}")
	public ResponseEntity<Object> getCartItems(@PathVariable Long userId) {
	    List<CartItem> cartItems = cartService.getCartItems(userId);
	    if (cartItems.isEmpty()) {
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "Empty cart");
	        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(response);
	    } else {
	        return ResponseEntity.ok(cartItems);
	    }
	}

	/**
	 * POST /api/cart : Add a new item to the cart.
	 * 
	 * @param cartItem the item to add
	 * @return the added cart item
	 */
	@PostMapping
	public CartItem addCartItem(@RequestBody CartItem cartItem) {
		return cartService.addCartItem(cartItem);
	}

	/**
	 * DELETE /api/cart/{id} : Remove an item from the cart.
	 * 
	 * @param id the id of the item to remove
	 */
	@DeleteMapping("/{id}")
	public void removeCartItem(@PathVariable Long id) {
		cartService.removeCartItem(id);
	}

	/**
	 * DELETE /api/cart/clear/{userId} : Clear all items from the cart for a
	 * specific user.
	 * 
	 * @param userId the id of the user
	 */
	@DeleteMapping("/clear/{userId}")
	public void clearCart(@PathVariable Long userId) {
		cartService.clearCart(userId);
	}
}