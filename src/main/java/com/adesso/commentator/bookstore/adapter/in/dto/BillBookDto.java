package com.adesso.commentator.bookstore.adapter.in.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class BillBookDto {

    private Long id;

    @Min(1)
    @Max(6)
    private int orderAmount;

    @DecimalMin("0")
    @DecimalMax("20")
    private double discount;

}
