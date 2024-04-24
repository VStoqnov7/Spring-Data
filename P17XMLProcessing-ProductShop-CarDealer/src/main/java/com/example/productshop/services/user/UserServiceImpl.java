package com.example.productshop.services.user;

import com.example.productshop.entities.product.Product;
import com.example.productshop.entities.product.ProductSoldDTO;
import com.example.productshop.entities.user.*;
import com.example.productshop.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.productshop.enums.OutXmlPaths.*;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper =  new ModelMapper();
        this.gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    }

    @Override
    public void getUsersWithSoldProducts() throws JAXBException, IOException {
        List<User> users = this.userRepository.findAllByBoughtProductsIsNotNullOrderByLastNameAscFirstNameAsc();

        List<UserSoldProductsDTO> userSoldProductsDTOS = users.stream()
                .map(user -> modelMapper.map(user,UserSoldProductsDTO.class))
                .collect(Collectors.toList());

        UserSoldProductsContainerDTO containerDTO = new UserSoldProductsContainerDTO();
        containerDTO.setUsers(userSoldProductsDTOS);

        JAXBContext context = JAXBContext.newInstance(UserSoldProductsContainerDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        try (FileWriter writer = new FileWriter(SUCCESSFULLY_SOLD_PRODUCTS)) {
            marshaller.marshal(containerDTO, writer);
        }
    }

    @Override
    public void getAllUsersAndProducts() throws JAXBException, IOException {
        List<User> users = this.userRepository.findAllBySoldProductsIsNotNullOrderBySoldProductsDescFirstNameAsc();

        List<UsersAndProductDTO> usersAndProducts = users.stream()
                .map(user -> modelMapper.map(user, UsersAndProductDTO.class))
                .collect(Collectors.toList());

        UsersAndProductContainerDTO containerDTO = new UsersAndProductContainerDTO();
        containerDTO.setUsers(usersAndProducts);
        containerDTO.setCount(usersAndProducts.size());

        JAXBContext context = JAXBContext.newInstance(UsersAndProductContainerDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        try (FileWriter writer = new FileWriter(USERS_AND_PRODUCTS)) {
            marshaller.marshal(containerDTO, writer);
        }
    }
}
