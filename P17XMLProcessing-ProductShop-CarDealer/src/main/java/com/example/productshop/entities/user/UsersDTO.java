package com.example.productshop.entities.user;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersDTO {

    @XmlElement(name = "user")
    private List<UserImportDTO> users;

    public List<UserImportDTO> getUsers() {
        return users;
    }
}
