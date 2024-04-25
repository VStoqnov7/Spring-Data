package com.example.productshop.repositories;


import com.example.productshop.entities.category.Category;
import com.example.productshop.entities.category.CategoryByProductCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("SELECT NEW com.example.productshop.entities.category.CategoryByProductCountDTO(c.name, COUNT(DISTINCT p.id), AVG(p.price), SUM(p.price)) " +
            "FROM Category c " +
            "JOIN c.products p " +
            "GROUP BY c.id, c.name " +
            "ORDER BY COUNT(p) DESC")
    List<CategoryByProductCountDTO> findAllCategoriesByProductsCount();
}
