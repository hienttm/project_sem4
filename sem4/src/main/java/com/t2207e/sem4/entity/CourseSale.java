package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course_sales")
@Getter
@Setter
@NoArgsConstructor

public class CourseSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseSaleId;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
