package com.example.productsshop.services.category;

import com.example.productsshop.entities.category.CategoryByProductCountDTO;
import com.example.productsshop.repositories.CategoryRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.example.productsshop.enums.OutJsonPaths.CATEGORIES_BY_PRODUCTS_COUNT;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    private final Gson gson;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void categoriesByProductsCount() {
        List<CategoryByProductCountDTO> categories = categoryRepository.findAllCategoriesByProductsCount();

        try (FileWriter writer = new FileWriter(CATEGORIES_BY_PRODUCTS_COUNT)) {
            gson.toJson(categories, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
