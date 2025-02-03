package com.example.demo.service;

import com.example.demo.entity.Book;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing books.
 */
public interface BookService {

	/**
	 * Retrieves all books.
	 * 
	 * @return list of books
	 */
	List<Book> getAllBooks();

	/**
	 * Retrieves a book by its ID.
	 * 
	 * @param id the ID of the book
	 * @return an Optional containing the book if found, or empty if not found
	 */
    Optional<Book> getBookById(Long id);

    /**
     * Saves a book.
     * @param book the book to save
     * @return the saved book
     */
    Book saveBook(Book book);

    /**
     * Deletes a book by its ID.
     * @param id the ID of the book to delete
     */
    void deleteBook(Long id);

    /**
     * Searches for books based on various criteria.
     * @param title the title of the book (optional)
     * @param author the author of the book (optional)
     * @param genre the genre of the book (optional)
     * @param minPrice the minimum price of the book (optional)
     * @param maxPrice the maximum price of the book (optional)
     * @return a list of books matching the search criteria
     */
    List<Book> searchBooks(String title, String author, String genre, Double minPrice, Double maxPrice);
}