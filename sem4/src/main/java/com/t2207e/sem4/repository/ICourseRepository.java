package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICourseRepository extends JpaRepository<Course, Integer> {
    @Procedure(name = "GetAllCourseProcedure")
    List<Object[]> GetAllCourseProcedure(@Param("searchName") String searchName);
}
