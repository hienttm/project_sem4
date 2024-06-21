package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "status_courses")
@Getter
@Setter
@NoArgsConstructor

public class StatusCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int statusId;

    @NotNull(message = "statusName cannot be null")
    private String statusName;

    @NotNull(message = "updateAt cannot be null")
    private Date updateAt;

    private String description;

    private String color;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
