package com.learning.LibraryManagementSystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Billing {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(targetEntity = BorrowBook.class,cascade = CascadeType.ALL)
    private BorrowBook borrowBook;
    private Date returnDate;
    private double finePerDay = 10;
    private double billAmount;
}
