package com.example.productsshop.services.user;

import com.example.productsshop.entities.product.ProductInRangeDTO;
import com.example.productsshop.entities.product.ProductSoldDTO;
import com.example.productsshop.entities.product.ProductsNameAndPriceDTO;
import com.example.productsshop.entities.product.SoldProductsCountDTO;
import com.example.productsshop.entities.user.User;
import com.example.productsshop.entities.user.UserWithSoldProductCountDTO;
import com.example.productsshop.entities.user.UserWithSoldProductsDTO;

import com.example.productsshop.entities.user.UsersAndProductsDTO;
import com.example.productsshop.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.productsshop.enums.OutJsonPaths.SUCCESSFULLY_SOLD_PRODUCTS;
import static com.example.productsshop.enums.OutJsonPaths.USERS_AND_PRODUCTS;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    }

    @Override
    public void getUsersWithSoldProducts() {
        List<User> users = this.userRepository.findAllByBoughtProductsIsNotNullOrderByLastNameAscFirstNameAsc();

        List<UserWithSoldProductsDTO> productsDTO = users.stream()
                .map(product -> modelMapper.map(product, UserWithSoldProductsDTO.class))
                .collect(Collectors.toList());

        try (FileWriter writer = new FileWriter(SUCCESSFULLY_SOLD_PRODUCTS)) {
            gson.toJson(productsDTO, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void getAllUsersAndProducts() {

        List<User> users = this.userRepository.findAllBySoldProductsIsNotNullOrderBySoldProductsDescFirstNameAsc();

        UsersAndProductsDTO usersAndProductsDTO = new UsersAndProductsDTO();
        usersAndProductsDTO.setUsersCount(users.size());
        List<UserWithSoldProductCountDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            UserWithSoldProductCountDTO userDTO = modelMapper.map(user, UserWithSoldProductCountDTO.class);
            SoldProductsCountDTO soldProductsDTO = new SoldProductsCountDTO();
            soldProductsDTO.setCount(user.getSoldProducts().size());
            List<ProductsNameAndPriceDTO> productDTOs = user.getSoldProducts().stream()
                    .map(product -> modelMapper.map(product, ProductsNameAndPriceDTO.class))
                    .collect(Collectors.toList());
            soldProductsDTO.setProducts(productDTOs);
            userDTO.setSoldProducts(soldProductsDTO);
            userDTOs.add(userDTO);
        }

        usersAndProductsDTO.setUsers(userDTOs);

        try (FileWriter writer = new FileWriter(USERS_AND_PRODUCTS)) {
            gson.toJson(usersAndProductsDTO, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
