package com.example.demo.BookServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookServiceImpl;

/**
 * Test class for BookServiceImpl.
 * Uses JUnit and Mockito to test the service's methods.
 */

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest{

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

    /**
     * Sets up the test data before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setGenre("Test Genre");
        book.setPrice(100.0);
    }

    /**
     * Tests the getAllBooks method.
     */
    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        List<Book> books = bookService.getAllBooks();
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("Test Book", books.get(0).getTitle());
    }

    /**
     * Tests the getBookById method.
     */
    @Test
    void testGetBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getBookById(1L);
        assertTrue(foundBook.isPresent());
        assertEquals("Test Book", foundBook.get().getTitle());
    }

    /**
     * Tests the saveBook method.
     */
    @Test
    void testSaveBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookService.saveBook(book);
        assertNotNull(savedBook);
        assertEquals("Test Book", savedBook.getTitle());
    }

    /**
     * Tests the deleteBook method.
     */
    @Test
    void testDeleteBook() {
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    /**
     * Tests the searchBooks method.
     */
    @Test
    void testSearchBooks() {
        when(bookRepository.searchBooks(anyString(), anyString(), anyString(), anyDouble(), anyDouble()))
            .thenReturn(Arrays.asList(book));

        List<Book> books = bookService.searchBooks("Test Book", "Test Author", "Test Genre", 50.0, 150.0);
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("Test Book", books.get(0).getTitle());
    }
}	