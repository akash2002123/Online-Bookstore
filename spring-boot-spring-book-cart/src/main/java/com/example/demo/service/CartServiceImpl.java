package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

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
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Internal-Request", "true"); // Custom header to indicate internal request
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<BookDTO> response = restTemplate.exchange(BOOK_SERVICE_URL + cartItem.getBookId(), HttpMethod.GET, entity, BookDTO.class);
            BookDTO book = response.getBody();
            if (book != null) {
                cartItem.setBookName(book.getTitle());
                cartItem.setPrice(book.getPrice()); // Set the price
            } else {
                throw new ResourceNotFoundException("Book not found for ID: " + cartItem.getBookId());
            }
        }
        return cartItems;
    }

    @Override
    public CartItem addCartItem(CartItem cartItem) {
        // Fetch book details from book management microservice
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Internal-Request", "true"); // Custom header to indicate internal request
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<BookDTO> response = restTemplate.exchange(BOOK_SERVICE_URL + cartItem.getBookId(), HttpMethod.GET, entity, BookDTO.class);
        BookDTO book = response.getBody();
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