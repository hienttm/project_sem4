package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "answers")
@Getter
@Setter
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int answer_id;

    @Column(nullable = false)
    private String answerContent;

    @Column(nullable = false)
    private boolean tof;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToMany(mappedBy = "answer")
    private List<UserAnswer> userAnswers;
}
