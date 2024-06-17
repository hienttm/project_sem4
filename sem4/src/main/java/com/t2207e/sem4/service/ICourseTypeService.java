package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.CourseType;

import java.util.List;
import java.util.Optional;

public interface ICourseTypeService {
    List<CourseType> getAllCourseType();
    Optional<CourseType> getCourseTypeById(Integer id);
    void add(CourseType courseType);
    void deleteById(Integer id);
    Boolean existsByTypeName(String typeName);
}
