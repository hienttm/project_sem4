package com.t2207e.sem4.service;

import com.t2207e.sem4.dto.CourseDTO;
import com.t2207e.sem4.dto.OrderDetailByUserDTO;
import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.User;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ICourseService {
    List<Course> getAllCourse();
    List<Object> getAllCoursesSelected();
    Optional<Course> getCourseById(Integer id);
    void add(Course course);
    void deleteById(Integer id);
    List<CourseDTO> GetAllCourseProcedure(String searchName);
    Integer countCoursesByStatus(Integer status);
    List<CourseDTO> GetAllCourseProcedurePaging(String searchName, Integer page, Integer pageSize, String course_type_name);
    Integer countCoursesByCourseNameContainingAndStatusAndCourseType_TypeNameContaining(String courseName, Integer status, String courseTypeName);
    List<Course> getCoursesByUser(User user);
    List<Object> getCoursesByUserSelected(User user);
    List<Course> getCoursesByUserAndStatus(User user, Integer status);
    List<OrderDetailByUserDTO> GetOrderDetailByUserIdProcedure(Integer userIdSearch);
    List<Course> findTopCourses();
}
