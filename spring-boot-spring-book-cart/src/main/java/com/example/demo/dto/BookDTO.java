package com.example.demo.dto;

import lombok.Data;

/**
 * Data Transfer Object for transferring book information between the Book
 * Management microservice and other parts of the application.
 */
@Data
public class BookDTO {
	private Long id;
	private String title;
	private String author;
	private double price;
}