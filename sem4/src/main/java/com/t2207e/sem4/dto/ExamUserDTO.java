package com.t2207e.sem4.dto;

import com.t2207e.sem4.entity.Exam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExamUserDTO {
    Exam exam;
    Integer countAnswers;
    Integer countTrueAnswers;
}
