package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    @NotNull(message = "starNumber cannot be null")
    private int starNumber;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(nullable = false)
    @NotNull(message = "status cannot be null")
    private int status = 1;

    private int featured = 2;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date createAt = new Date(System.currentTimeMillis());

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date updateAt = new Date(System.currentTimeMillis());
}
