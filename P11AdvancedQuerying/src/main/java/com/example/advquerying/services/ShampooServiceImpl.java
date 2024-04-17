package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class ShampooServiceImpl implements ShampooService{

    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }


    @Override
    public List<Shampoo> findBySizeOrderBySizeAscIdAsc(Size size) {
        return shampooRepository.findBySizeOrderBySizeAscIdAsc(size);
    }

    @Override
    public List<Shampoo> findAllBySizeOrLabelIdOrderByPrice(Size size, Long id) {
        return this.shampooRepository.findAllBySizeOrLabelIdOrderByPrice(size,id);
    }

    @Override
    public List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(BigDecimal price) {
        return this.shampooRepository.findByPriceGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public int countByPriceLessThan(BigDecimal price) {
        return this.shampooRepository.countByPriceLessThan(price);
    }

    @Override
    public List<Shampoo> findByIngredientsNames(Set<String> ingredients) {
        return  this.shampooRepository.findByIngredientsNames(ingredients);
    }

    @Override
    public List<Shampoo> findByIngredientCountLessThan(int count) {
        return this.shampooRepository.findByIngredientCountLessThan(count);
    }
}
