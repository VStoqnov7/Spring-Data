package com.example.p01bookshopsystem.services;

import com.example.p01bookshopsystem.entities.Category;

import java.util.Set;

public interface CategoryService {
    Set<Category> getRandomCategories();
}
