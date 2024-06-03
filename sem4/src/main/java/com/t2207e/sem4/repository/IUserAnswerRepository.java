package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserAnswerRepository extends JpaRepository<UserAnswer, Integer> {
}
