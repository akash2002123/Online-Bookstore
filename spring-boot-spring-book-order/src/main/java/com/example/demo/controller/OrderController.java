package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrderRequestDTO;
import com.example.demo.dto.OrderResponseDTO;
import com.example.demo.service.OrderService;

/**
 * REST controller for managing orders. Provides endpoints for creating,
 * retrieving, and processing orders.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

	/**
	 * POST /api/orders/checkout : Create a new order.
	 * 
	 * @param orderRequestDTO the order request data transfer object
	 * @return the created order response data transfer object
	 */
	@PostMapping("/checkout")
	public OrderResponseDTO checkout(@RequestBody OrderRequestDTO orderRequestDTO) {
		return orderService.createOrder(orderRequestDTO);
	}

	/**
	 * POST /api/orders/pay/{orderId} : Process payment for an order.
	 * 
	 * @param orderId the ID of the order to pay for
	 * @return a confirmation message with delivery details
	 */
	@PostMapping("/pay/{orderId}")
	public String pay(@PathVariable Long orderId) {
		OrderResponseDTO order = orderService.processPayment(orderId);
		return "Payment successful! Your order will be delivered to " + order.getAddress() + " by "
				+ order.getDeliveryDate().toString() + ".";
	}

	/**
	 * GET /api/orders/{orderId} : Get an order by its ID.
	 * 
	 * @param orderId the ID of the order
	 * @return the order response data transfer object
	 */
	@GetMapping("/{orderId}")
	public OrderResponseDTO getOrder(@PathVariable Long orderId) {
		return orderService.getOrder(orderId);
	}

	/**
	 * GET /api/orders/user/{userId} : Get all orders for a specific user.
	 * 
	 * @param userId the ID of the user
	 * @return a list of order response data transfer objects
	 */
	@GetMapping("/user/{userId}")
	public List<OrderResponseDTO> getOrdersByUserId(@PathVariable Long userId) {
		return orderService.getOrdersByUserId(userId);
	}
}