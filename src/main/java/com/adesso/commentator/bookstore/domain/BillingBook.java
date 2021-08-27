package com.adesso.commentator.bookstore.domain;


import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;

public class BillingBook {

    private static long bookCount;

    @Getter
    private final long id;

    @Getter
    @Setter
    private long bookId;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String author;

    @Getter
    @Setter
    private int publicationYear;

    @Getter
    @Setter
    private int orderAmount;

    @Getter
    @Setter
    private double discount;

    @Getter
    @Setter
    private double price;

    @Getter
    @Setter
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
