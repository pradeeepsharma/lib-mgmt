package com.learning.LibraryManagementSystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    private String category;
    private String bookName;
    private String bookUrl;
    private int availableBooks;
    private String status;
    @OneToMany(targetEntity = BorrowBook.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "bookId")
    private List<BorrowBook> bookings;


}
