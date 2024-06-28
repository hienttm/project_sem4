package com.t2207e.sem4.service;

import com.t2207e.sem4.dto.OrderDetailByUserDTO;
import com.t2207e.sem4.dto.UserDoExamDTO;
import com.t2207e.sem4.entity.Exam;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.entity.UserAnswer;
import com.t2207e.sem4.repository.IUserAnswerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserAnswerService implements IUserAnswerService{
    private final IUserAnswerRepository userAnswerRepository;

    @Autowired
    public UserAnswerService(IUserAnswerRepository userAnswerRepository) {
        this.userAnswerRepository = userAnswerRepository;
    }

    @Override
    public List<UserAnswer> getAllUserAnswer() {
        return userAnswerRepository.findAll();
    }

    @Override
    public Optional<UserAnswer> getUserAnswerById(Integer id) {
        return userAnswerRepository.findById(id);
    }

    @Override
    public void add(UserAnswer userAnswer) {
        userAnswerRepository.save(userAnswer);
    }

    @Override
    public void deleteById(Integer id) {
        userAnswerRepository.deleteById(id);
    }

    @Override
    public Integer countUserAnswerByUserAndExam_ExamId(User user, Integer examId) {
        return userAnswerRepository.countUserAnswerByUserAndExam_ExamId(user, examId);
    }

    @Override
    public List<UserAnswer> getUserAnswersByUserAndExam_ExamId(User user, Integer examId) {
        return userAnswerRepository.getUserAnswersByUserAndExam_ExamId(user, examId);
    }

    @Override
    public void deleteUserAnswersByUserAndExam(User user, Exam exam) {
        userAnswerRepository.deleteUserAnswersByUserAndExam(user, exam);
    }

    @Override
    public List<UserAnswer> findUserAnswersByAnswer_Question_Exam_ExamId(Integer examId) {
        return userAnswerRepository.findUserAnswersByAnswer_Question_Exam_ExamId(examId);
    }

    @Override
    public List<UserAnswer> getUserAnswersByExam_ExamId(Integer examId) {
        return userAnswerRepository.getUserAnswersByExam_ExamId(examId);
    }

    @Override
    @Transactional
    public List<UserDoExamDTO> GetUserDoExamProcedure(Integer examIdSearch) {
        List<Object[]> resultList = userAnswerRepository.GetUserDoExamProcedure(examIdSearch);
        return resultList.stream()
                .map(result -> {
                    UserDoExamDTO userDoExamDTO = new UserDoExamDTO();
                    userDoExamDTO.setFullName((String) result[0]);
                    userDoExamDTO.setTrueNumber((BigDecimal) result[1]);
                    userDoExamDTO.setTotalNumber((Long) result[2]);
                    userDoExamDTO.setEmail((String) result[3]);
                    userDoExamDTO.setPhoneNumber((String) result[4]);
                    return userDoExamDTO;
                })
                .collect(Collectors.toList());
    }
}
