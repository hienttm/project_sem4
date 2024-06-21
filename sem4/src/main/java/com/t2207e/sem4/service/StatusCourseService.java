package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.StatusCourse;
import com.t2207e.sem4.repository.IStatusCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusCourseService implements IStatusCourseService{
    private final IStatusCourseRepository statusCourseRepository;

    @Autowired
    public StatusCourseService(IStatusCourseRepository statusCourseRepository) {
        this.statusCourseRepository = statusCourseRepository;
    }

    @Override
    public List<StatusCourse> getAllStatusCourse() {
        return statusCourseRepository.findAll();
    }

    @Override
    public Optional<StatusCourse> getStatusCourseById(Integer id) {
        return statusCourseRepository.findById(id);
    }

    @Override
    public void add(StatusCourse statusCourse) {
        statusCourseRepository.save(statusCourse);
    }

    @Override
    public void deleteById(Integer id) {
        statusCourseRepository.deleteById(id);
    }

    @Override
    public List<StatusCourse> getStatusCoursesByCourse_CourseId(Integer id) {
        return statusCourseRepository.getStatusCoursesByCourse_CourseId(id);
    }
}
