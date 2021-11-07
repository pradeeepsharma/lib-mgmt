package com.learning.LibraryManagementSystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long userid;
    private String userName;
    private String emailId;
    @OneToMany(targetEntity = BorrowBook.class)
    private List<BorrowBook> myBookings;
    @OneToMany(targetEntity = Billing.class)
    private List<Billing> myBills;

    public User(long userId, String userName, String emailId, List<BorrowBook> myBookings) {
        this.userid = userId;
        this.userName = userName;
        this.emailId = emailId;
        this.myBookings = myBookings;
    }
}
