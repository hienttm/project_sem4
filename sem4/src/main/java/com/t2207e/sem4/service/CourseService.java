package com.t2207e.sem4.service;

import com.t2207e.sem4.dto.CourseDTO;
import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.repository.ICourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService implements  ICourseService{
    private final ICourseRepository courseRepository;

    @Autowired
    public CourseService(ICourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    @Override
    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getCourseById(Integer id) {
        return courseRepository.findById(id);
    }

    @Override
    public void add(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void deleteById(Integer id) {
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<CourseDTO> GetAllCourseProcedure() {
        List<Object[]> resultList = courseRepository.GetAllCourseProcedure();
        return resultList.stream()
                .map(result -> {
                    CourseDTO courseDTO = new CourseDTO();
                    courseDTO.setCourseId((Integer) result[0]);
                    courseDTO.setCourseFile((String) result[1]);
                    courseDTO.setCourseName((String) result[2]);
                    courseDTO.setCourseVideo((String) result[3]);
                    courseDTO.setFreeNumbers((Integer) result[4]);
                    courseDTO.setPrice((Double) result[5]);
                    courseDTO.setStatus((Integer) result[6]);
                    courseDTO.setUsername((String) result[7]);
                    courseDTO.setCensorName((String) result[8]);
                    courseDTO.setCategoryName((String) result[9]);
                    courseDTO.setCourseTypeName((String) result[10]);
                    return courseDTO;
                })
                .collect(Collectors.toList());
    }
}
