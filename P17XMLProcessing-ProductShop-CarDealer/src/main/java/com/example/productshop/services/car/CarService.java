package com.example.productshop.services.car;


import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public interface CarService {

    void exportToyotaCars() throws JAXBException, IOException;

    void exportAllCarsAndParts() throws JAXBException, IOException;
}
