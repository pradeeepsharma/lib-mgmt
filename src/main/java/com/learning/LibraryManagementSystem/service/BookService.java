package com.learning.LibraryManagementSystem.service;

import com.learning.LibraryManagementSystem.exception.BookNotFoundException;
import com.learning.LibraryManagementSystem.models.Book;
import com.learning.LibraryManagementSystem.models.BorrowBook;
import com.learning.LibraryManagementSystem.models.BorrowStatus;
import com.learning.LibraryManagementSystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    BookRepository repository;
    public Book getBook(Long bookId) throws BookNotFoundException {
        Optional<Book> bookOptional = repository.findById(bookId);

        if(bookOptional.isEmpty())
            throw new BookNotFoundException(bookId);

        Book book = bookOptional.get();
      //  List<BorrowBook> borrowings = book.getBookings().stream().filter(borrowBook -> borrowBook.getBorrowStatus().equals(BorrowStatus.BORROWED)).collect(Collectors.toList());
        book.setBookings(book.getBookings().stream().filter(borrowBook -> borrowBook.getBorrowStatus().equals(BorrowStatus.BORROWED)).collect(Collectors.toList()));
        return book;
    }

    public Book addBook(Book book) {
        return repository.save(book);
    }

    public List<Book> getBooks(){
        List<Book> books = new ArrayList<>();
        repository.findAll().forEach(books::add);
        return books;
    }

    public Book removeBook(Long bookId) throws BookNotFoundException {
       // Optional.ofNullable(repository.findById(bookId)).ifPresent(repository.deleteById(bookId));
        Optional<Book> book = repository.findById(bookId);
        if (book.isEmpty())
            throw new BookNotFoundException(bookId);
        repository.deleteById(bookId);
        return book.get();
    }

    public void returnBook(Long bookId) throws BookNotFoundException {
        Book book = getBook(bookId);
        book.setStatus("available");
        book.setAvailableBooks(book.getAvailableBooks()+1);
        addBook(book);
    }
}
