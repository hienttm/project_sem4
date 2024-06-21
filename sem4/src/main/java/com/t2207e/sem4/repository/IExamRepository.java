package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IExamRepository extends JpaRepository<Exam, Integer> {
    List<Exam> getExamsByCourse(Course course);
    List<Exam> getExamsByCourseAndStatus(Course course, Integer status);
}
