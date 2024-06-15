package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICourseTypeRepository extends JpaRepository<CourseType, Integer> {
    Boolean existsByTypeName(String typeName);
}
