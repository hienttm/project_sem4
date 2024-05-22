package com.t2207e.sem4.service;

import com.t2207e.sem4.dto.CourseDTO;
import com.t2207e.sem4.entity.Course;

import java.util.List;
import java.util.Optional;

public interface ICourseService {
    List<Course> getAllCourse();
    Optional<Course> getCourseById(Integer id);
    void add(Course course);
    void deleteById(Integer id);
    List<CourseDTO> GetAllCourseProcedure(String searchName);
}
