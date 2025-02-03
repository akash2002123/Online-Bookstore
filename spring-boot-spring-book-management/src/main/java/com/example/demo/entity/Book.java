package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Entity class representing a book.
 * Maps to a database table using JPA annotations.
 */
@Entity
@Data
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Title cannot be blank")
	private String title;

	@NotBlank(message = "Author cannot be blank")
	private String author;

	@NotBlank(message = "Genre cannot be blank")
	private String genre;

	@Positive(message = "Price must be positive")
	private double price;

}
