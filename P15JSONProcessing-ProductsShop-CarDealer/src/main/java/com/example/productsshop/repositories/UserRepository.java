package com.example.productsshop.repositories;

import com.example.productsshop.entities.user.User;
import com.example.productsshop.entities.user.UsersAndProductsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findAllByBoughtProductsIsNotNullOrderByLastNameAscFirstNameAsc();


    List<User> findAllBySoldProductsIsNotNullOrderBySoldProductsDescFirstNameAsc();

}
