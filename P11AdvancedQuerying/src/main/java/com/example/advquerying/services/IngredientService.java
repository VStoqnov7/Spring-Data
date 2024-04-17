package com.example.advquerying.services;


import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface IngredientService {

    List<Ingredient> findByNameStartsWith(String name);

    List<Ingredient> findByNameInOrderByPriceAsc(List<String> names);

    int deleteByName(String name);

    void increasePriceByPercent(BigDecimal percent);

    void increasePriceByName(BigDecimal percent, Set<String> names);
}
