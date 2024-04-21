package com.example.productsshop.entities.user;

import com.example.productsshop.entities.product.SoldProductsCountDTO;

import java.util.List;

public class UserWithSoldProductCountDTO {

    private String firstName;

    private String lastName;
    private int age;
    private SoldProductsCountDTO soldProducts;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public SoldProductsCountDTO getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(SoldProductsCountDTO soldProducts) {
        this.soldProducts = soldProducts;
    }
}
