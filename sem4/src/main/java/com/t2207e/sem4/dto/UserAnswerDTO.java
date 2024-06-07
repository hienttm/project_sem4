package com.t2207e.sem4.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserAnswerDTO {
    private Integer examId;
    private Integer questionId;
    private Integer answerId;
}