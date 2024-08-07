package com.t2207e.sem4.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeacherSalaryDTO {
    private Integer userId;
    private String username;
    private String fullName;
    private Double salary;
}
