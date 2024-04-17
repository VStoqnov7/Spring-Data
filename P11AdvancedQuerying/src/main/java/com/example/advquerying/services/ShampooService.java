package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ShampooService {
    List<Shampoo> findBySizeOrderBySizeAscIdAsc(Size size);

    List<Shampoo> findAllBySizeOrLabelIdOrderByPrice(Size size, Long id);

    List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    int countByPriceLessThan(BigDecimal price);

    List<Shampoo> findByIngredientsNames(Set<String> ingredients);

    List<Shampoo> findByIngredientCountLessThan(int count);
}
