package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.CategorySale;
import com.t2207e.sem4.repository.ICategorySaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorySaleService implements ICategorySaleService{
    private final ICategorySaleRepository categorySaleRepository;

    @Autowired
    public CategorySaleService(ICategorySaleRepository categorySaleRepository) {
        this.categorySaleRepository = categorySaleRepository;
    }

    @Override
    public List<CategorySale> getAllCategorySale() {
        return categorySaleRepository.findAll();
    }

    @Override
    public Optional<CategorySale> getCategorySaleById(Integer id) {
        return categorySaleRepository.findById(id);
    }

    @Override
    public void add(CategorySale categorySale) {
        categorySaleRepository.save(categorySale);
    }

    @Override
    public void deleteById(Integer id) {
        categorySaleRepository.deleteById(id);
    }
}
