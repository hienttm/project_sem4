package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Exam;
import com.t2207e.sem4.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IQuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> getQuestionsByExamAndStatus(Exam exam, Integer status);
}
