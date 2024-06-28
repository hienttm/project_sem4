package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Exam;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUserAnswerRepository extends JpaRepository<UserAnswer, Integer> {
    Integer countUserAnswerByUserAndExam_ExamId(User user, Integer examId);
    List<UserAnswer> getUserAnswersByUserAndExam_ExamId(User user, Integer examId);
    void deleteUserAnswersByUserAndExam(User user, Exam exam);
    List<UserAnswer> findUserAnswersByAnswer_Question_Exam_ExamId(Integer examId);
    List<UserAnswer> getUserAnswersByExam_ExamId(Integer examId);
    @Procedure(name = "GetUserDoExamProcedure")
    List<Object[]> GetUserDoExamProcedure(@Param("exam_id_search") Integer examIdSearch);
}
