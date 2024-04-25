package com.example.productshop.services.sale;

import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public interface SaleService {
    void exportSalesWithDiscounts() throws JAXBException, IOException;
}
