package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IQuestionRepository extends JpaRepository<Question, Integer> {
}
