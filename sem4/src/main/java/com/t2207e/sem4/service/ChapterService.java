package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Chapter;
import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.repository.IChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChapterService implements IChapterService{
    private final IChapterRepository chapterRepository;

    @Autowired
    public ChapterService(IChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }


    @Override
    public List<Chapter> getAllChapter() {
        return chapterRepository.findAll();
    }

    @Override
    public Optional<Chapter> getChapterById(Integer id) {
        return chapterRepository.findById(id);
    }

    @Override
    public void add(Chapter chapter) {
        chapterRepository.save(chapter);
    }

    @Override
    public void deleteById(Integer id) {
        chapterRepository.deleteById(id);
    }

    @Override
    public List<Chapter> getChaptersByCourse(Course course) {
        return chapterRepository.getChaptersByCourse(course);
    }

    @Override
    public List<Chapter> getChaptersByCourseAndStatus(Course course, Integer status) {
        return chapterRepository.getChaptersByCourseAndStatus(course, status);
    }
}
