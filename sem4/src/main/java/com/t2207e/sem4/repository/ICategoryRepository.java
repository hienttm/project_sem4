package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICategoryRepository extends JpaRepository<Category,Integer> {
//    List<Category> findByStatus(int status);
}
