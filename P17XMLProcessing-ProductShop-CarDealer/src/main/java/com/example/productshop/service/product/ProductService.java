package com.example.productshop.services.product;

import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.math.BigDecimal;

public interface ProductService {
    void getProductsInRangeWithNoBuyer(BigDecimal minPrice, BigDecimal maxPrice) throws IOException, JAXBException;
}
