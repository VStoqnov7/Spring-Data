package com.example.productshop.repositories;


import com.example.productshop.entities.car.Car;
import com.example.productshop.entities.category.Category;
import com.example.productshop.entities.category.CategoryByProductCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {

    List<Car> findAllByOrderByModelAscTravelledDistanceDesc();

}
