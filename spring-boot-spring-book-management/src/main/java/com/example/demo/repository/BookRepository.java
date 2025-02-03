package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;

/**
 * Repository interface for Book entities. 
 * Extends JpaRepository to provide CRUD operations.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	/**
	 * Custom query to search for books based on various criteria.
	 * 
	 * @param title    the title of the book (optional)
	 * @param author   the author of the book (optional)
	 * @param genre    the genre of the book (optional)
	 * @param minPrice the minimum price of the book (optional)
	 * @param maxPrice the maximum price of the book (optional)
	 * @return a list of books matching the search criteria
	 */
	@Query("SELECT b FROM Book b WHERE " + "(:title IS NULL OR b.title LIKE %:title%) AND "
			+ "(:author IS NULL OR b.author LIKE %:author%) AND " + "(:genre IS NULL OR b.genre LIKE %:genre%) AND "
			+ "(:minPrice IS NULL OR b.price >= :minPrice) AND " + "(:maxPrice IS NULL OR b.price <= :maxPrice)")
	List<Book> searchBooks(@Param("title") String title, @Param("author") String author, @Param("genre") String genre,
			@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

}