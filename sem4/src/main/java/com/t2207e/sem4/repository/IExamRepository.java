package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExamRepository extends JpaRepository<Exam, Integer> {
}
