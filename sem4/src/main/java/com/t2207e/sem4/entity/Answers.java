package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.patterns.TypePatternQuestions;

@Entity
@Table(name = "answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int answerId;

    @Column(nullable = false)
    private String answerContent;

    @Column(nullable = false)
    private boolean tof;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

}
