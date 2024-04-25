package com.example.productshop.entities.user;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersAndProductContainerDTO {

    @XmlAttribute
    private int count;

    @XmlElement(name = "user")
    private List<UsersAndProductDTO> users;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<UsersAndProductDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UsersAndProductDTO> users) {
        this.users = users;
    }
}
