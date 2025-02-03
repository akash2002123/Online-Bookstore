package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.OrderRequestDTO;
import com.example.demo.dto.OrderResponseDTO;

/**
 * OrderService interface provides methods to handle order-related operations.
 * This includes creating orders, processing payments, retrieving orders, and
 * fetching orders by user ID.
 */
public interface OrderService {
	OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

	OrderResponseDTO processPayment(Long orderId);

	OrderResponseDTO getOrder(Long orderId);

	List<OrderResponseDTO> getOrdersByUserId(Long userId);
}
