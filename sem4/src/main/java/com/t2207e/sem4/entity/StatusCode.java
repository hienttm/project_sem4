package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "status_codes")
@Getter
@Setter
@NoArgsConstructor

public class StatusCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int statusId;

    @NotNull(message = "statusName cannot be null")
    private String statusName;

    @NotNull(message = "updateAt cannot be null")
    private Date updateAt;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
