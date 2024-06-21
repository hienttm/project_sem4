package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.Exam;

import java.util.List;
import java.util.Optional;

public interface IExamService {
    List<Exam> getAllExam();
    Optional<Exam> getExamById(Integer id);
    void add(Exam exam);
    void deleteById(Integer id);
    List<Exam> getExamsByCourse(Course course);

    List<Exam> getExamsByCourseAndStatus(Course course, Integer status);
}
