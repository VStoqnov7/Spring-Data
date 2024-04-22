package com.example.productsshop.services;

import java.io.FileNotFoundException;

public interface SeedService {
    void seedUsers() throws FileNotFoundException;

    void seedCategories() throws FileNotFoundException;

    void seedProducts() throws FileNotFoundException;
    void seedSuppliers() throws FileNotFoundException;
    void seedParts() throws FileNotFoundException;
    void seedCars() throws FileNotFoundException;
    void seedCustomers() throws FileNotFoundException;
    void seedSales() throws FileNotFoundException;

    default void seedAll() throws FileNotFoundException {
        seedUsers();
        seedCategories();
        seedProducts();
        seedSuppliers();
        seedParts();
        seedCars();
        seedCustomers();
        seedSales();
    }
}
