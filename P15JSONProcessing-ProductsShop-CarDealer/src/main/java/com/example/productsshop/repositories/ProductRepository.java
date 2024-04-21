package com.example.productsshop.repositories;

import com.example.productsshop.entities.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product AS p " +
            "WHERE p.price BETWEEN :minPrice AND :maxPrice " +
            "AND p.buyer IS NULL " +
            "ORDER BY p.price ")
    List<Product> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal minPrice, BigDecimal maxPrice);
}
