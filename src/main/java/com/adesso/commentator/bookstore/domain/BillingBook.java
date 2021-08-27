package com.adesso.commentator.bookstore.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;

public @Data class BillingBook {

    private static long bookCount;

    private final long id;

    private long bookId;

    private String title;

    private String author;

    private int publicationYear;

    private int orderAmount;

    private double discount;

    private double price;

    private double totalAmount;

    public BillingBook(@Valid Book book, int orderAmount, double discount) {
        this.id = bookCount++;
        this.bookId = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publicationYear = book.getPublicationYear();

        if (orderAmount < 1 || orderAmount > 6)
            throw new IllegalArgumentException("order Amount out of range");

        this.orderAmount = orderAmount;

        if (discount < 0 || discount > 20)
            throw new IllegalArgumentException("discount out of range");

        this.discount = discount;

        this.price = book.getPrice();

        this.totalAmount = price * orderAmount * (1-(discount/100));
    }
}
