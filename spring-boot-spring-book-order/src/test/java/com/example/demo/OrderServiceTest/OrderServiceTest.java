package com.example.demo.OrderServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.dto.OrderRequestDTO;
import com.example.demo.dto.OrderResponseDTO;
import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderServiceImpl;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;
    private OrderRequestDTO orderRequestDTO;
    private CartItemDTO cartItem1;
    private CartItemDTO cartItem2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        cartItem1 = new CartItemDTO();
        cartItem1.setBookId(1L);
        cartItem1.setQuantity(2);
        cartItem1.setPrice(100.0);

        cartItem2 = new CartItemDTO();
        cartItem2.setBookId(2L);
        cartItem2.setQuantity(1);
        cartItem2.setPrice(50.0);

        orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setUserId(1L);
        orderRequestDTO.setAddress("123 Main St");

        order = new Order();
        order.setOrderId(1L);
        order.setUserId(1L);
        order.setBookIds(Arrays.asList(1L, 2L));
        order.setTotalAmount(250.0);
        order.setAddress("123 Main St");
        order.setPaymentStatus("PENDING");
        order.setDeliveryDate(LocalDate.now().plusDays(3));
    }

    @Test
    public void testCreateOrder() {
        CartItemDTO[] cartItems = { cartItem1, cartItem2 };
        when(restTemplate.getForObject("http://localhost:8085/api/cart/1", CartItemDTO[].class)).thenReturn(cartItems);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderResponseDTO result = orderService.createOrder(orderRequestDTO);

        assertEquals(1L, result.getOrderId());
        assertEquals(1L, result.getUserId());
        assertEquals(250.0, result.getTotalAmount());
        assertEquals("123 Main St", result.getAddress());
        assertEquals("PENDING", result.getPaymentStatus());
    }

    @Test
    public void testProcessPayment() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderResponseDTO result = orderService.processPayment(1L);

        assertEquals("PAID", result.getPaymentStatus());
        assertEquals(order.getDeliveryDate(), result.getDeliveryDate());
        verify(restTemplate, times(1)).delete("http://localhost:8085/api/cart/clear/1");
    }

    @Test
    public void testProcessPaymentOrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            orderService.processPayment(1L);
        });
    }

    @Test
    public void testGetOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        OrderResponseDTO result = orderService.getOrder(1L);
        assertEquals(1L, result.getOrderId());
        assertEquals(1L, result.getUserId());
        assertEquals(250.0, result.getTotalAmount());
        assertEquals("123 Main St", result.getAddress());
    }

    @Test
    public void testGetOrdersByUserId() {
        List<Order> orders = Arrays.asList(order);
        when(orderRepository.findByUserId(1L)).thenReturn(orders);

        List<OrderResponseDTO> result = orderService.getOrdersByUserId(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getOrderId());
        assertEquals(1L, result.get(0).getUserId());
        assertEquals(250.0, result.get(0).getTotalAmount());
        assertEquals("123 Main St", result.get(0).getAddress());
    }
}