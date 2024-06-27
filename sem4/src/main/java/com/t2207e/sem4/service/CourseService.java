package com.t2207e.sem4.service;

import com.t2207e.sem4.dto.CourseDTO;
import com.t2207e.sem4.dto.OrderDetailByUserDTO;
import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.CourseType;
import com.t2207e.sem4.entity.User;
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
    public List<CourseDTO> GetAllCourseProcedure(String searchName) {
        List<Object[]> resultList = courseRepository.GetAllCourseProcedure(searchName);
        return getCourseDTOS(resultList);
    }

    @Override
    @Transactional
    public List<CourseDTO> GetAllCourseProcedurePaging(String searchName, Integer page, Integer pageSize,String course_type_name) {
        List<Object[]> resultList = courseRepository.GetAllCourseProcedurePaging(searchName, page, pageSize, course_type_name);
        return getCourseDTOS(resultList);
    }

    @Override
    public Integer countCoursesByCourseNameContainingAndStatusAndCourseType_TypeNameContaining(String courseName, Integer status, String courseTypeName) {
        return courseRepository.countCoursesByCourseNameContainingAndStatusAndCourseType_TypeNameContaining(courseName, status, courseTypeName);
    }

    @Override
    public List<Course> getCoursesByUser(User user) {
        return courseRepository.getCoursesByUser(user);
    }

    @Override
    @Transactional
    public List<OrderDetailByUserDTO> GetOrderDetailByUserIdProcedure(Integer userIdSearch) {
        List<Object[]> resultList = courseRepository.GetOrderDetailByUserIdProcedure(userIdSearch);
        return resultList.stream()
                .map(result -> {
                    OrderDetailByUserDTO orderDetailByUserDTO = new OrderDetailByUserDTO();
                    orderDetailByUserDTO.setUserId((Integer) result[0]);
                    orderDetailByUserDTO.setUsername((String) result[1]);
                    orderDetailByUserDTO.setDescription((String) result[2]);
                    orderDetailByUserDTO.setCourseName((String) result[3]);
                    orderDetailByUserDTO.setCourseId(result[4]!=null?(Integer) result[4]:-1);
                    return orderDetailByUserDTO;
                })
                .collect(Collectors.toList());
    }


    private List<CourseDTO> getCourseDTOS(List<Object[]> resultList) {
        return resultList.stream()
                .map(result -> {
                    CourseDTO courseDTO = new CourseDTO();
                    courseDTO.setCourseId((Integer) result[0]);
                    courseDTO.setCourseFile((String) result[1]);
                    courseDTO.setCourseName((String) result[2]);
                    courseDTO.setCourseVideo((String) result[3]);
                    courseDTO.setFreeNumbers((Integer) result[4]);
                    courseDTO.setImage((String) result[5]);
                    courseDTO.setPrice((Double) result[6]);
                    courseDTO.setSalePrice((Double) result[7]);
                    courseDTO.setStatus((Integer) result[8]);
                    courseDTO.setUsername((String) result[9]);
                    courseDTO.setCensorName((String) result[10]);
                    courseDTO.setCategoryName((String) result[11]);
                    courseDTO.setCourseTypeName((String) result[12]);
                    return courseDTO;
                })
                .collect(Collectors.toList());
    }
}
