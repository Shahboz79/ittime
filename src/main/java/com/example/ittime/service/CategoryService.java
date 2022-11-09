package com.example.ittime.service;

import com.example.ittime.entity.Category;
import com.example.ittime.repository.CategoryRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CategoryService {

   final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<Category> getAllCategoriess() {
        return categoryRepository.findAll();
    }
}
