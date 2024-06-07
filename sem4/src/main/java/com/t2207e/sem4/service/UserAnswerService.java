package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.entity.UserAnswer;
import com.t2207e.sem4.repository.IUserAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
