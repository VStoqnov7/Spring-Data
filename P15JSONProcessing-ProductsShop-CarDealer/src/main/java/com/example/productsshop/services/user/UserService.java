package com.example.productsshop.services.user;

import com.example.productsshop.entities.user.User;

import java.util.List;

public interface UserService {

    void getUsersWithSoldProducts();

    void getAllUsersAndProducts();
}
