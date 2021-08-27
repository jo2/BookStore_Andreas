package com.adesso.commentator.bookstore.adapter.out.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bill_id")
    public long id;

    @Column(name="date")
    public Date date;

    @Column(name="total_price")
    public double totalPrice;

    @OneToMany(mappedBy = "billing_book_id")
    public List<BillingBook> books;
}
