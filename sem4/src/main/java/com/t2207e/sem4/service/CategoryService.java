package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Category;
import com.t2207e.sem4.repository.ICategoryRepository;
import com.t2207e.sem4.repository.ICourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ICategoryRepository categoryRepository;

    public List<Category> getAllCategory(){
        return  categoryRepository.findAll();
    }
    public Optional<Category> getCategoryId(Integer id) {
        return categoryRepository.findById(id);
    }

    public void addCategory (Category category) {
        categoryRepository.save(category);
    }
    public void updateCategory( Category updateCategory) {
        categoryRepository.save(updateCategory);
    }
    public void deleteCategoryId(Integer id){
        categoryRepository.deleteById(id);
    }

    // Phương thức mới để lấy các danh mục có trạng thái hiển thị
//    public List<Category> getVisibleCategories() {
//        return categoryRepository.findByStatus(1);
//    }
}
