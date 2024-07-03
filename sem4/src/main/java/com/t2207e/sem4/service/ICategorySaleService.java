package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.CategorySale;

import java.util.List;
import java.util.Optional;

public interface ICategorySaleService {
    List<CategorySale> getAllCategorySale();
    Optional<CategorySale> getCategorySaleById(Integer id);
    void add(CategorySale categorySale);
    void deleteById(Integer id);
}
