package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;

/**
 * REST controller for managing books. Provides endpoints for creating,
 * retrieving, updating, and deleting books.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	/**
	 * GET /api/books : Get all books.
	 * 
	 * @return the list of books
	 */
	@GetMapping
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}

	/**
	 * GET /api/books/{id} : Get a book by its ID.
	 * 
	 * @param id the ID of the book
	 * @return the ResponseEntity with status 200 (OK) and with body the book, or
	 *         with status 404 (Not Found)
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		Optional<Book> book = bookService.getBookById(id);
		return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	/**
	 * POST /api/books : Create a new book.
	 * 
	 * @param book the book to create
	 * @return the created book
	 */
	@PostMapping
	public Book createBook(@RequestBody Book book) {
		return bookService.saveBook(book);
	}

	/**
	 * PUT /api/books/{id} : Update an existing book.
	 * 
	 * @param id          the ID of the book to update
	 * @param bookDetails the updated book details
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         book, or with status 404 (Not Found)
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
		Optional<Book> book = bookService.getBookById(id);
		if (book.isPresent()) {
			Book updatedBook = book.get();
			updatedBook.setTitle(bookDetails.getTitle());
			updatedBook.setAuthor(bookDetails.getAuthor());
			updatedBook.setGenre(bookDetails.getGenre());
			updatedBook.setPrice(bookDetails.getPrice());
			return ResponseEntity.ok(bookService.saveBook(updatedBook));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * DELETE /api/books/{id} : Delete a book by its ID.
	 * 
	 * @param id the ID of the book to delete
	 * @return the ResponseEntity with status 204 (No Content)
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * GET /api/books/search : Search for books by various criteria.
	 * 
	 * @param title    the title of the book
	 * @param author   the author of the book
	 * @param genre    the genre of the book
	 * @param minPrice the minimum price of the book
	 * @param maxPrice the maximum price of the book
	 * @return the list of books matching the search criteria
	 */
	@GetMapping("/search")
	public List<Book> searchBooks(@RequestParam(required = false) String title,
			@RequestParam(required = false) String author, @RequestParam(required = false) String genre,
			@RequestParam(required = false) Double minPrice, @RequestParam(required = false) Double maxPrice) {
		return bookService.searchBooks(title, author, genre, minPrice, maxPrice);
	}
}