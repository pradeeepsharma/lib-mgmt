package com.learning.LibraryManagementSystem.service;

import com.learning.LibraryManagementSystem.exception.BillNotFoundException;
import com.learning.LibraryManagementSystem.exception.BookNotFoundException;
import com.learning.LibraryManagementSystem.models.Billing;
import com.learning.LibraryManagementSystem.models.BorrowBook;
import com.learning.LibraryManagementSystem.models.BorrowStatus;
import com.learning.LibraryManagementSystem.repository.BillingRepository;
import com.learning.LibraryManagementSystem.repository.BorrowBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Service
public class BillingService {
    @Autowired
    BillingRepository billingRepository;
    @Autowired
    BorrowBookService borrowBookService;
    @Autowired
    BookService bookService;

    @Transactional
    public void returnBook(Long borrowBookId) throws BookNotFoundException {
        BorrowBook borrowBook = borrowBookService.getBorrowBook(borrowBookId);
        borrowBook.setBorrowStatus(BorrowStatus.RETURNED);
        borrowBookService.updateBooking(borrowBook);

        bookService.returnBook(borrowBook.getBookId());

        Billing billing = generateBill(borrowBook);
        billingRepository.save(billing);
    }

    private Billing generateBill(BorrowBook borrowBook){

        Billing billing = new Billing();

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDate borrowedDate = LocalDate.parse(borrowBook.getBookingDate().toString(), formatter);
        Long totalDays =  ChronoUnit.DAYS.between(borrowedDate,today);
        double totalAmount = (totalDays*borrowBook.getChargePerDay())+(billing.getFinePerDay()*(totalDays-borrowBook.getNoOfDays()));

        billing.setBillAmount(totalAmount);
        billing.setReturnDate(Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        billing.setBorrowBook(borrowBook);
        return billing;


    }

    public Billing getBill(Long billId)throws BillNotFoundException{
        Optional<Billing> billingOptional = billingRepository.findById(billId);
        if(billingOptional.isEmpty())
            throw new BillNotFoundException("No bill for requested id :"+billId);
        return billingOptional.get();
    }
}
