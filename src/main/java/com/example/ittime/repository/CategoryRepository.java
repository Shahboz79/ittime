package com.example.ittime.repository;

import com.example.ittime.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
