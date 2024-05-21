package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "exams")
@Getter
@Setter
@NoArgsConstructor
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "examId")
    private int examId;

    @Column(name = "examName")
    @NotNull(message = "ExamName cannot be null")
    private String examName;

    @Column(name = "createAt")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "createAt cannot be null")
    private Date createAt = new Date(System.currentTimeMillis());

    @Column(name = "numberQuestion")
    @NotNull(message = "Number Question cannot be null")
    private int numberQuestion;

    @Column(name = "ratioPass")
    @NotNull(message = "RatioPass cannot be null")
    private int ratioPass;

    @Column(name = "status",nullable = false)
    private int status=1;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // Getters and Setters
}
