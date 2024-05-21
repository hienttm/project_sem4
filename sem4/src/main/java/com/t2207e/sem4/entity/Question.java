package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int questionId;

    @Column(nullable = false)
    private int question;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

}
