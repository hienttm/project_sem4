package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "CourseTypes")
@Getter
@Setter
@NoArgsConstructor

public class CourseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courseTypeId")
    private int courseTypeId;

    @Column(name = "typeName")
    @NotNull(message = "TypeName cannot be null")
    private String typeName;

    @Column(name ="status")
    @NotNull(message = "Status cannot be null")
    private int status=1;

    @Column(name = "createAt")
    @NotNull(message = "CreateAt cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date createAt=new Date(System.currentTimeMillis());

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    // Getters and Setters
}

