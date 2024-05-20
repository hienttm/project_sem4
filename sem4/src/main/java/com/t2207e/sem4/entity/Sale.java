package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "sales")
@Getter
@Setter
@NoArgsConstructor

public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int saleId;

    @NotNull(message = "sale cannot be null")
    private double sale;

    @NotNull(message = "minPrice cannot be null")
    private double minPrice;

    @NotNull(message = "maxSale cannot be null")
    private double maxSale;

    @NotNull(message = "quantity cannot be null")
    private int quantity;

    @NotNull(message = "startAt cannot be null")
    private Date startAt;

    @NotNull(message = "endAt cannot be null")
    private Date endAt;

    @NotNull(message = "status cannot be null")
    private int status;

    @ManyToOne
    @JoinColumn(name = "category_sale_id")
    private CategorySale categorySale;
}
