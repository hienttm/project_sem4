package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String statusName = "CREATE";

    @NotNull(message = "updateAt cannot be null")
    private Date updateAt = new Date(System.currentTimeMillis());

    private String description = "Creating...";

    private String color = "secondary";

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
