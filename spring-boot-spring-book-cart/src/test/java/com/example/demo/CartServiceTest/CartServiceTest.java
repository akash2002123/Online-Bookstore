package com.example.demo.CartServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.BookDTO;
import com.example.demo.entity.CartItem;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CartRepository;
import com.example.demo.service.CartServiceImpl;

public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CartServiceImpl cartService;

    private CartItem cartItem1;
    private CartItem cartItem2;
    private BookDTO book1;
    private BookDTO book2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cartItem1 = new CartItem();
        cartItem1.setId(1L);
        cartItem1.setUserId(1L);
        cartItem1.setBookId(1L);
        cartItem1.setQuantity(2);
        cartItem1.setPrice(100.0);

        cartItem2 = new CartItem();
        cartItem2.setId(2L);
        cartItem2.setUserId(1L);
        cartItem2.setBookId(2L);
        cartItem2.setQuantity(1);
        cartItem2.setPrice(50.0);

        book1 = new BookDTO();
        book1.setId(1L);
        book1.setTitle("Book One");
        book1.setPrice(100.0);

        book2 = new BookDTO();
        book2.setId(2L);
        book2.setTitle("Book Two");
        book2.setPrice(50.0);
    }

    @Test
    public void testGetCartItems() {
        List<CartItem> cartItems = Arrays.asList(cartItem1, cartItem2);
        when(cartRepository.findByUserId(1L)).thenReturn(cartItems);
        when(restTemplate.getForObject("http://localhost:8085/api/books/1", BookDTO.class)).thenReturn(book1);
        when(restTemplate.getForObject("http://localhost:8085/api/books/2", BookDTO.class)).thenReturn(book2);

        List<CartItem> result = cartService.getCartItems(1L);

        assertEquals(2, result.size());
        assertEquals("Book One", result.get(0).getBookName());
        assertEquals(100.0, result.get(0).getPrice());
        assertEquals("Book Two", result.get(1).getBookName());
        assertEquals(50.0, result.get(1).getPrice());
    }

    @Test
    public void testGetCartItemsBookNotFound() {
        List<CartItem> cartItems = Arrays.asList(cartItem1);
        when(cartRepository.findByUserId(1L)).thenReturn(cartItems);
        when(restTemplate.getForObject("http://localhost:8085/api/books/1", BookDTO.class)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            cartService.getCartItems(1L);
        });
    }

    @Test
    public void testAddCartItem() {
        when(restTemplate.getForObject("http://localhost:8085/api/books/1", BookDTO.class)).thenReturn(book1);
        when(cartRepository.save(cartItem1)).thenReturn(cartItem1);

        CartItem result = cartService.addCartItem(cartItem1);

        assertEquals("Book One", result.getBookName());
        assertEquals(100.0, result.getPrice());
    }

    @Test
    public void testAddCartItemBookNotFound() {
        when(restTemplate.getForObject("http://localhost:8085/api/books/1", BookDTO.class)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            cartService.addCartItem(cartItem1);
        });
    }

    @Test
    public void testRemoveCartItem() {
        doNothing().when(cartRepository).deleteById(1L);

        cartService.removeCartItem(1L);

        verify(cartRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testClearCart() {
        List<CartItem> cartItems = Arrays.asList(cartItem1, cartItem2);
        when(cartRepository.findByUserId(1L)).thenReturn(cartItems);
        doNothing().when(cartRepository).deleteAll(cartItems);

        cartService.clearCart(1L);

        verify(cartRepository, times(1)).deleteAll(cartItems);
    }
}
