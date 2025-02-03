package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;

/**
 * Implementation of the BookService interface. Provides methods for managing
 * books.
 */
@Service
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	/**
	 * Retrieves all books.
	 * 
	 * @return list of books
	 */
	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	/**
	 * Retrieves a book by its ID.
	 * 
	 * @param id the ID of the book
	 * @return an Optional containing the book if found, or empty if not found
	 */
	@Override
	public Optional<Book> getBookById(Long id) {
		return bookRepository.findById(id);
	}

	/**
	 * Saves a book.
	 * 
	 * @param book the book to save
	 * @return the saved book
	 */
	@Override
	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}

	/**
	 * Deletes a book by its ID.
	 * 
	 * @param id the ID of the book to delete
	 */
	@Override
	public void deleteBook(Long id) {
		bookRepository.deleteById(id);
	}

	/**
	 * Searches for books based on various criteria.
	 * 
	 * @param title    the title of the book (optional)
	 * @param author   the author of the book (optional)
	 * @param genre    the genre of the book (optional)
	 * @param minPrice the minimum price of the book (optional)
	 * @param maxPrice the maximum price of the book (optional)
	 * @return a list of books matching the search criteria
	 */
	@Override
	public List<Book> searchBooks(String title, String author, String genre, Double minPrice, Double maxPrice) {
		return bookRepository.searchBooks(title, author, genre, minPrice, maxPrice);
	}
}