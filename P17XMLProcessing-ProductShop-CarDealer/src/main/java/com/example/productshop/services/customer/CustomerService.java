package com.example.productshop.services.customer;

import jakarta.xml.bind.JAXBException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface CustomerService {

    void orderedCustomers() throws JAXBException, IOException;

    void exportCustomersWithCars() throws JAXBException, IOException;
}
