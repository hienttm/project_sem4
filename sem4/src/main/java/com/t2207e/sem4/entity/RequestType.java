package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "request_types")
@Getter
@Setter
@NoArgsConstructor

public class RequestType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestTypeId;

    @Column(nullable = false)
    private String requestTypeName;

    @Column(nullable = false)
    private int status;

    private String description;
}
