package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.StatusCourse;

import java.util.List;
import java.util.Optional;

public interface IStatusCourseService {
    List<StatusCourse> getAllStatusCourse();
    Optional<StatusCourse> getStatusCourseById(Integer id);
    void add(StatusCourse statusCourse);
    void deleteById(Integer id);
    List<StatusCourse> getStatusCoursesByCourse_CourseId(Integer id);
}
