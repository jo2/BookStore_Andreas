package com.adesso.commentator.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Bill {

    private static long billCount;

    private final long id;

    private LocalDateTime date;

    private final List<BillingBook> books;

    private double totalPrice;

    public Bill(List<BillingBook> books) {
        id = billCount++;
        this.books = books;
        this.totalPrice = books.stream().map(BillingBook::getTotalAmount).reduce(Double::sum).orElse(0.0);
    }
}
