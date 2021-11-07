package com.learning.LibraryManagementSystem.controllers;

import com.learning.LibraryManagementSystem.exception.BookNotFoundException;
import com.learning.LibraryManagementSystem.models.Book;
import com.learning.LibraryManagementSystem.models.Book;
import com.learning.LibraryManagementSystem.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookControllers {
    private static final Logger logger = LoggerFactory.getLogger(BookControllers.class);

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/books/{id}", produces = "application/json")
    public ResponseEntity<Book> getBook(@PathVariable(name = "id") Long bookId) throws BookNotFoundException {
        logger.info("Fetching details of book with id :"+bookId);
        Book book = bookService.getBook(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping(value = "/books", produces = "application/json")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        logger.info("Adding Book "+book);
        bookService.addBook(book);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
    @GetMapping(value = "/books", produces = "application/json")
    public ResponseEntity<List<Book>> getBooks(){
        List<Book> books = bookService.getBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @DeleteMapping(value = "/books/{id}", produces = "application/json")
    public ResponseEntity<Book> getRidOfBook(@PathVariable(name = "id") Long bookId) throws BookNotFoundException {
        Book book = bookService.removeBook(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
