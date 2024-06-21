package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Chapter;
import com.t2207e.sem4.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IChapterRepository extends JpaRepository<Chapter, Integer> {
    List<Chapter> getChaptersByCourse(Course course);
    List<Chapter> getChaptersByCourseAndStatus(Course course, Integer status);
}
