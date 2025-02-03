package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object for transferring order response data.
 */
@Data
public class OrderResponseDTO {
	private Long orderId;
	private Long userId;
	private List<Long> bookIds;
	private Double totalAmount;
	private String address;
	private String paymentStatus;
	private LocalDate deliveryDate;
}