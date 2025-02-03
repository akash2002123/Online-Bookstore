package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.BookDTO;
import com.example.demo.entity.CartItem;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CartRepository;

/**
 * Implementation of the CartService interface. Provides methods for managing
 * cart items, including interaction with the Book Management microservice.
 */
@Service
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final RestTemplate restTemplate;

	private static final String BOOK_SERVICE_URL = "http://localhost:8085/api/books/";

	@Autowired
	public CartServiceImpl(CartRepository cartRepository, RestTemplate restTemplate) {
		this.cartRepository = cartRepository;
		this.restTemplate = restTemplate;
	}

	/**
	 * Retrieves all cart items for a specific user. Fetches book details from the
	 * Book Management microservice.
	 * 
	 * @param userId the ID of the user
	 * @return a list of cart items belonging to the user
	 */
	@Override
	public List<CartItem> getCartItems(Long userId) {
		List<CartItem> cartItems = cartRepository.findByUserId(userId);
		for (CartItem cartItem : cartItems) {
			BookDTO book = restTemplate.getForObject(BOOK_SERVICE_URL + cartItem.getBookId(), BookDTO.class);
			if (book != null) {
				cartItem.setBookName(book.getTitle());
				cartItem.setPrice(book.getPrice()); // Set the price
			} else {
				throw new ResourceNotFoundException("Book not found for ID: " + cartItem.getBookId());
			}
		}
		return cartItems;
	}

	/**
	 * Adds a new item to the cart. Fetches book details from the Book Management
	 * microservice.
	 * 
	 * @param cartItem the item to add
	 * @return the added cart item
	 */
	@Override
	public CartItem addCartItem(CartItem cartItem) {
		// Fetch book details from book management microservice
		BookDTO book = restTemplate.getForObject(BOOK_SERVICE_URL + cartItem.getBookId(), BookDTO.class);
		if (book != null) {
			cartItem.setBookName(book.getTitle());
			cartItem.setPrice(book.getPrice()); // Set the price
			// Save cart item with book details
			return cartRepository.save(cartItem);
		} else {
			throw new ResourceNotFoundException("Book not found for ID: " + cartItem.getBookId());
		}
	}

	/**
	 * Removes an item from the cart.
	 * 
	 * @param id the ID of the item to remove
	 */
	@Override
	public void removeCartItem(Long id) {
		cartRepository.deleteById(id);
	}

	/**
	 * Clears all items from the cart for a specific user.
	 * 
	 * @param userId the ID of the user
	 */
	@Override
	public void clearCart(Long userId) {
		List<CartItem> cartItems = cartRepository.findByUserId(userId);
		cartRepository.deleteAll(cartItems);
	}
}