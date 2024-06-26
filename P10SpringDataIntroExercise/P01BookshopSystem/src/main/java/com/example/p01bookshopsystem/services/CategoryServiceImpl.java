package com.example.p01bookshopsystem.services;

import com.example.p01bookshopsystem.entities.Category;
import com.example.p01bookshopsystem.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Set<Category> getRandomCategories() {
        Random random = new Random();
        long size = this.categoryRepository.count();

        // 10 -> [0..9] -> [1..10]
        int categoriesCount = random.nextInt((int) size) + 1;

        Set<Integer> categoriesIds = new HashSet<>();

        for (int i = 0; i < categoriesCount; i++) {
            int nextId = random.nextInt((int) size) + 1;

            categoriesIds.add(nextId);
        }

        List<Category> allById = this.categoryRepository.findAllById(categoriesIds);

        return new HashSet<>(allById);
    }
}
