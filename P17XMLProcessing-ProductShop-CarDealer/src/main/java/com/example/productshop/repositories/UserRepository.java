package com.example.productshop.repositories;

import com.example.productshop.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findAllByBoughtProductsIsNotNullOrderByLastNameAscFirstNameAsc();
    List<User> findAllBySoldProductsIsNotNullOrderBySoldProductsDescFirstNameAsc();
}
