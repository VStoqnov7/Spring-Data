package com.example.productsshop.entities.user;


import java.util.List;
import java.util.stream.Collectors;

public class UsersAndProductsDTO {

    private int usersCount;

    private List<UserWithSoldProductCountDTO> users;


    public List<UserWithSoldProductCountDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithSoldProductCountDTO> users) {
        this.users = users;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }
}
