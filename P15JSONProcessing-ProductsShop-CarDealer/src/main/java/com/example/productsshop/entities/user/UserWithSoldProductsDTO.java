package com.example.productsshop.entities.user;

import com.example.productsshop.entities.product.ProductSoldDTO;

import java.util.List;

public class UserWithSoldProductsDTO {

    private String firstName;
    private String lastName;
    private List<ProductSoldDTO> soldProducts;

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

    public List<ProductSoldDTO> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductSoldDTO> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
