package com.example.productshop.services;

import jakarta.xml.bind.JAXBException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface SeedService {
    void seedUsers() throws FileNotFoundException, JAXBException;

    void seedCategories() throws JAXBException, FileNotFoundException;

    void seedProducts() throws JAXBException, FileNotFoundException;
    void seedSuppliers() throws JAXBException, IOException;
    void seedParts() throws JAXBException, IOException;
    void seedCars() throws JAXBException, IOException;
    void seedCustomers() throws JAXBException, IOException;
    void seedSales() ;

    default void seedAll() throws IOException, JAXBException {
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
