package com.example.productshop.entities.user;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSoldProductsContainerDTO {

    @XmlElement(name = "user")
    private List<UserSoldProductsDTO> users;

    public List<UserSoldProductsDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserSoldProductsDTO> users) {
        this.users = users;
    }

}
