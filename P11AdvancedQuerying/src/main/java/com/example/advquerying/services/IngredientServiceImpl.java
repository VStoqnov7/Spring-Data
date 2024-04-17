package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class IngredientServiceImpl implements IngredientService{
    private IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> findByNameStartsWith(String name) {
       return this.ingredientRepository.findByNameStartsWith(name);
    }

    @Override
    public List<Ingredient> findByNameInOrderByPriceAsc(List<String> names) {
        return this.ingredientRepository.findByNameInOrderByPriceAsc(names);
    }

    @Override
    @Transactional
    public int deleteByName(String name) {
        return this.ingredientRepository.deleteByName(name);
    }

    @Override
    @Transactional
    public void increasePriceByPercent(BigDecimal percent) {
        this.ingredientRepository.increasePriceByPercent(percent);
    }

    @Override
    @Transactional
    public void increasePriceByName(BigDecimal percent, Set<String> names) {
        this.ingredientRepository.increasePriceByName(percent,names);
    }



}
