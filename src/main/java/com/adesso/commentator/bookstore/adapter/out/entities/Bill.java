package com.adesso.commentator.bookstore.adapter.out.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bill_id")
    public long id;

    @CreatedDate
    @Column(name="date", columnDefinition = "TEMPORAL")
    public LocalDateTime date;

    @Column(name="total_price")
    public double totalPrice;

    @OneToMany(mappedBy = "id")
    public List<BillingBook> books;
}