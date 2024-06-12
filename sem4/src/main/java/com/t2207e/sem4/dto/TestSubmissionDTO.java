package com.t2207e.sem4.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TestSubmissionDTO {
    private List<UserAnswerDTO> userAnswerDTOs;
    private Integer examId;
    private Integer courseId;
}
