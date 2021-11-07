package com.learning.LibraryManagementSystem.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BorrowBook {

    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    /*@ManyToOne(targetEntity = Book.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Book book;*/
    private Long bookId;
    @JsonDeserialize
    private Date bookingDate;
    private double chargePerDay;
    private int noOfDays;
    private BorrowStatus borrowStatus;
}
