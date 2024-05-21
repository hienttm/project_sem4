package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "teacher_profits")
@Getter
@Setter
@NoArgsConstructor

public class TeacherProfit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teacherProfitId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private double profit;

}
