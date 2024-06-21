package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Chapter;
import com.t2207e.sem4.entity.Course;

import java.util.List;
import java.util.Optional;

public interface IChapterService {
    List<Chapter> getAllChapter();
    Optional<Chapter> getChapterById(Integer id);
    void add(Chapter chapter);
    void deleteById(Integer id);
    List<Chapter> getChaptersByCourse(Course course);
    List<Chapter> getChaptersByCourseAndStatus(Course course, Integer status);
}
