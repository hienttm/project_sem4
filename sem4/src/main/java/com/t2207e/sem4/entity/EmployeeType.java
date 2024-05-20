package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee_types")
@Getter
@Setter
@NoArgsConstructor
public class EmployeeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeTypeId;

    @Column(nullable = false)
    private String employeeTypeName;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Getters and Setters
}

