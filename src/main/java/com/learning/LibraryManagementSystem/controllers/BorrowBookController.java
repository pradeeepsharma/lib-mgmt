package com.learning.LibraryManagementSystem.controllers;

import com.learning.LibraryManagementSystem.exception.BookNotFoundException;
import com.learning.LibraryManagementSystem.models.Book;
import com.learning.LibraryManagementSystem.models.BorrowBook;
import com.learning.LibraryManagementSystem.models.BorrowStatus;
import com.learning.LibraryManagementSystem.service.BookService;
import com.learning.LibraryManagementSystem.service.BorrowBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class BorrowBookController {
    private static final Logger logger = LoggerFactory.getLogger(BorrowBookController.class);
    @Autowired
    private BorrowBookService borrowBookService;
    @Autowired
    private BookService bookService;

    @PostMapping("/borrowbook")
    public ResponseEntity<BorrowBook> addBorrowBookEntry(@RequestBody BorrowBook borrowBook) throws BookNotFoundException {
        logger.info("Borrowing a book :" + borrowBook);
        BorrowBook borrowedBook = new BorrowBook();
        Long bookId = borrowBook.getBookId();
        if(ifBookIsAvailable(bookId)) {
            borrowBook.setBookingDate(new Date());
            borrowBook.setBorrowStatus(BorrowStatus.BORROWED);
            borrowedBook = borrowBookService.borrowABook(borrowBook);
            updateBookAvailability(bookId);
        }
        return new ResponseEntity<>(borrowedBook, HttpStatus.CREATED);
    }
    @GetMapping("/borrowbook/{borrowId}")
    public ResponseEntity<BorrowBook> getBorrowBook(@PathVariable Long borrowId){
        return new ResponseEntity<>(borrowBookService.getBorrowBook(borrowId), HttpStatus.OK);
    }
    @GetMapping("/borrowbook")
    public ResponseEntity<List<BorrowBook>> getBorrowBooks(){
        return new ResponseEntity<>(borrowBookService.getBorrowBooks(), HttpStatus.OK);
    }

    private boolean ifBookIsAvailable(Long bookId) throws BookNotFoundException {
            return bookService.getBook(bookId).getAvailableBooks()>0;
    }

    private boolean updateBookAvailability(Long bookId) throws BookNotFoundException {
        boolean updated = true;
        Book book = bookService.getBook(bookId);
        int count =book.getAvailableBooks()-1;
        book.setAvailableBooks(count);
        if(count==0)
            book.setStatus("Not Available");
        bookService.addBook(book);
        return updated;
    }
}
