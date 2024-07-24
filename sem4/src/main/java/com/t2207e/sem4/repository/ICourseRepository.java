package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICourseRepository extends JpaRepository<Course, Integer> {
    @Procedure(name = "GetAllCourseProcedure")
    List<Object[]> GetAllCourseProcedure(@Param("searchName") String searchName);

    @Procedure(name = "GetAllCourseProcedurePaging")
    List<Object[]> GetAllCourseProcedurePaging(@Param("searchName") String searchName, @Param("page") Integer page, @Param("pageSize") Integer pageSize, @Param("course_type_name") String course_type_name);

    Integer countCoursesByCourseNameContainingAndStatusAndCourseType_TypeNameContaining(String courseName, Integer status, String courseTypeName);

    List<Course> getCoursesByUser(User user);
    @Query("SELECT c.courseId, c.courseName, c.price, c.status, c.user.fullname, c.courseType.typeName, c.censor.fullname FROM Course c WHERE c.user = :user")
    List<Object> getCoursesByUserSelected(@Param("user") User user);
    List<Course> getCoursesByUserAndStatus(User user, Integer status);

    Integer countCoursesByStatus(Integer status);

    @Procedure(name = "GetOrderDetailByUserIdProcedure")
    List<Object[]> GetOrderDetailByUserIdProcedure(@Param("user_id_search") Integer userIdSearch);

    @Query("SELECT c FROM Course c WHERE c.status = 1 ORDER BY c.courseId DESC")
    List<Course> findTopCourses(Pageable pageable);

    @Query("SELECT c.courseId, c.courseName, c.price, c.status, c.user.fullname, c.courseType.typeName, c.censor.fullname FROM Course c")
    List<Object> getAllCoursesSelected();
}
