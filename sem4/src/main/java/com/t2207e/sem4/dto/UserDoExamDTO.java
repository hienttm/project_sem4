package com.t2207e.sem4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDoExamDTO {
    private String fullName;
    private BigDecimal trueNumber;
    private Long totalNumber;
    private String email;
    private String phoneNumber;
}
