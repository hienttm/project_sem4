package com.t2207e.sem4.service;

import com.t2207e.sem4.dto.UserDoExamDTO;
import com.t2207e.sem4.entity.Exam;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.entity.UserAnswer;

import java.util.List;
import java.util.Optional;

public interface IUserAnswerService {
    List<UserAnswer> getAllUserAnswer();
    Optional<UserAnswer> getUserAnswerById(Integer id);
    void add(UserAnswer userAnswer);
    void deleteById(Integer id);

    Integer countUserAnswerByUserAndExam_ExamId(User user, Integer examId);

    List<UserAnswer> getUserAnswersByUserAndExam_ExamId(User user, Integer examId);

    void deleteUserAnswersByUserAndExam(User user, Exam exam);
    List<UserAnswer> findUserAnswersByAnswer_Question_Exam_ExamId(Integer examId);
    List<UserAnswer> getUserAnswersByExam_ExamId(Integer examId);

    List<UserDoExamDTO> GetUserDoExamProcedure(Integer examIdSearch);
}
