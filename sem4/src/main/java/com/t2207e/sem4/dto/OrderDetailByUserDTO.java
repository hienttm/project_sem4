package com.t2207e.sem4.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailByUserDTO {
    private int userId;

    private String username;

    private String description;

    private String courseName;

    private int CourseId;
}
