package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.StatusCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IStatusCourseRepository extends JpaRepository<StatusCourse, Integer> {
    List<StatusCourse> getStatusCoursesByCourse_CourseId(Integer id);
}
