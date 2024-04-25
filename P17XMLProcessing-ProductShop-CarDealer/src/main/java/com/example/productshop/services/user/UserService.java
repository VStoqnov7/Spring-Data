package com.example.productshop.services.user;

import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public interface UserService {

    void getUsersWithSoldProducts() throws JAXBException, IOException;

    void getAllUsersAndProducts() throws JAXBException, IOException;
}
