package com.adesso.commentator.bookstore.domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public @Data class Bill {

    private static long billCount;

    private final long id;

    private final LocalDate date;

    private final List<BillingBook> books;

    private final double totalPrice;

    public Bill(List<BillingBook> books) {
        id = billCount++;
        date = LocalDate.now();
        this.books = books;
        this.totalPrice = books.stream().map(BillingBook::getTotalAmount).reduce(Double::sum).orElse(0.0);
    }
}
