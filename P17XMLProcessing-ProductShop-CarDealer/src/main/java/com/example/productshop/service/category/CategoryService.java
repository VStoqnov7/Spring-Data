package com.example.productshop.services.category;

import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public interface CategoryService {

    void categoriesByProductsCount() throws JAXBException, IOException;
}
