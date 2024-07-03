package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startAt;

    @NotNull(message = "endAt cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endAt;

    @NotNull(message = "quantity cannot be null")
    @Min(value = 1, message = "quantity must be greater than 1")
    private int quantity;

    @NotNull(message = "code cannot be null")
    private String code;

    @NotNull(message = "sale cannot be null")
    @Min(value = 0, message = "Sale must be greater than 0")
    private double sale;

    @NotNull(message = "maxSale cannot be null")
    @Min(value = 0, message = "maxSale must be greater than 0")
    private double maxSale;

    @NotNull(message = "minPrice cannot be null")
    @Min(value = 0, message = "minPrice must be greater than 0")
    private double minPrice;

    @NotNull(message = "area cannot be null")
    private int area;

    @NotNull(message = "status cannot be null")
    private int status=1;

    @NotNull(message = "description cannot be null")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "categorySale cannot be null")
    private CategorySale categorySale;
}
