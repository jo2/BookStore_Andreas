package com.adesso.commentator.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@AllArgsConstructor
public @Data class Book {

    private final long id;

    @Size(min=2, max=30)
    private String title;

    @Size(min=2, max=20)
    private String author;

    @DecimalMin("1.0")
    private double price;

    @Min(1000)
    @Max(2050)
    private int publicationYear;

    @Min(0)
    private int stockAmount;
}
