package com.example.productshop.services.supplier;

import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public interface SupplierService {

    void exportSuppliers() throws JAXBException, IOException;
}
