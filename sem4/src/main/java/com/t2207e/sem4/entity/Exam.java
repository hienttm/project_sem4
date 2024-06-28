package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;
import java.util.List;

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
    private int numberQuestion=0;

    @Column(name = "ratioPass")
    private int ratioPass;

    @Column(name = "time")
    @NotNull(message = "Time cannot be null")
    @Min(value = 1, message = "Time must be more than 1 minute")
    private int time;

    @Column(name = "status",nullable = false)
    private int status=1;

    private String description;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "exam")
    private List<Question> questions;
    // Getters and Setters
}
