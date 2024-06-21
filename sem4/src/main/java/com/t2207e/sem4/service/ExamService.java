package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.Exam;
import com.t2207e.sem4.repository.IExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamService implements IExamService{
    private final IExamRepository examRepository;

    @Autowired
    public ExamService(IExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public List<Exam> getAllExam() {
        return examRepository.findAll();
    }

    @Override
    public Optional<Exam> getExamById(Integer id) {
        return examRepository.findById(id);
    }

    @Override
    public void add(Exam exam) {
        examRepository.save(exam);
    }

    @Override
    public void deleteById(Integer id) {
        examRepository.deleteById(id);
    }

    @Override
    public List<Exam> getExamsByCourse(Course course) {
        return examRepository.getExamsByCourse(course);
    }
    @Override
    public List<Exam> getExamsByCourseAndStatus(Course course, Integer status) {
        return examRepository.getExamsByCourseAndStatus(course, 1);
    }

}
