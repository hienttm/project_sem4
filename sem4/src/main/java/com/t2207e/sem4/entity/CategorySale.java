package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "category_sales")
@Getter
@Setter
@NoArgsConstructor

public class CategorySale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categorySaleId;

    @NotNull(message = "categorySaleName cannot be null")
    private String categorySaleName;

    @NotNull(message = "description cannot be null")
    private String description;
}
