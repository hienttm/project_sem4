package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserAnswerRepository extends JpaRepository<UserAnswer, Integer> {
    Integer countUserAnswerByUserAndExam_ExamId(User user, Integer examId);
    List<UserAnswer> getUserAnswersByUserAndExam_ExamId(User user, Integer examId);
}
