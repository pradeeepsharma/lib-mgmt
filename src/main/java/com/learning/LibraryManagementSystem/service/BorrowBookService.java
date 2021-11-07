package com.learning.LibraryManagementSystem.service;

import com.learning.LibraryManagementSystem.models.BorrowBook;
import com.learning.LibraryManagementSystem.repository.BorrowBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BorrowBookService {

    @Autowired
    BorrowBookRepository borrowBookRepository;

    public BorrowBook borrowABook(BorrowBook borrowBook) {
        BorrowBook bookBorrowed = borrowBookRepository.save(borrowBook);
        return bookBorrowed;
    }

    public BorrowBook getBorrowBook(Long borrowBookId){
        return borrowBookRepository.findById(borrowBookId).get();
    }

    public List<BorrowBook> getBorrowBooks() {
        List<BorrowBook> borrowBooks = new ArrayList<>();
        borrowBookRepository.findAll().forEach(borrowBooks::add);
        return borrowBooks;
    }

    public void updateBooking(BorrowBook borrowBook) {
        borrowBookRepository.save(borrowBook);
    }
}
