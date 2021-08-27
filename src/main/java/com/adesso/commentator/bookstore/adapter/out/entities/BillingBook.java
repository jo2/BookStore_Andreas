package com.adesso.commentator.bookstore.adapter.out.entities;

import javax.persistence.*;

@Entity
public class BillingBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="billing_book_id")
    public long id;

    @Column(name="book_id")
    public long bookId;

    @Column(name="title")
    public String title;

    @Column(name="author")
    public String author;

    @Column(name="publicaction_year")
    public int publicationYear;

    @Column(name="order_amount")
    public int orderAmount;

    @Column(name="discount")
    public double discount;

    @Column(name="price")
    public double price;

    @Column(name="total_amount")
    public double totalAmount;

}
