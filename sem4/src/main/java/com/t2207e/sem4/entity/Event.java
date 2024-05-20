package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventId;

    @NotNull(message = "eventName cannot be null")
    private String eventName;

    @NotNull(message = "startAt cannot be null")
    private Date startAt;

    @NotNull(message = "endAt cannot be null")
    private Date endAt;

    @NotNull(message = "quantity cannot be null")
    private int quantity;

    @NotNull(message = "code cannot be null")
    private String code;

    @NotNull(message = "sale cannot be null")
    private double sale;

    @NotNull(message = "maxSale cannot be null")
    private int maxSale;

    @NotNull(message = "minPrice cannot be null")
    private int minPrice;

    @NotNull(message = "status cannot be null")
    private int status;

    @NotNull(message = "description cannot be null")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategorySale categorySale;
}
