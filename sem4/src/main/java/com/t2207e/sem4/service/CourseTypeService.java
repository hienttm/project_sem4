package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.CourseType;
import com.t2207e.sem4.repository.ICourseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseTypeService implements ICourseTypeService{
    private final ICourseTypeRepository courseTypeRepository;

    @Autowired
    public CourseTypeService(ICourseTypeRepository courseTypeRepository) {
        this.courseTypeRepository = courseTypeRepository;
    }

    @Override
    public List<CourseType> getAllCourseType() {
        return courseTypeRepository.findAll();
    }

    @Override
    public Optional<CourseType> getCourseTypeById(Integer id) {
        return courseTypeRepository.findById(id);
    }

    @Override
    public void add(CourseType courseType) {
        courseTypeRepository.save(courseType);
    }

    @Override
    public void deleteById(Integer id) {
        courseTypeRepository.deleteById(id);
    }

    @Override
    public Boolean existsByTypeName(String typeName) {
        return courseTypeRepository.existsByTypeName(typeName);
    }
}
