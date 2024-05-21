package com.t2207e.sem4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "categories")
@Data
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="categoryId")
    private int categoryId;

    @Column(name = "categoryName")
    @NotNull(message = "CategoryName cannot be null")
    private String categoryName;

    @Column(name = "status")
    @NotNull(message = "Status cannot be null")
    private int status=1;

    @Column(name="updateAt")

    private Date updateAt;

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;
}
