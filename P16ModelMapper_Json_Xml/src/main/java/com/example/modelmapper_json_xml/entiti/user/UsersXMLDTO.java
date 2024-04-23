package com.example.modelmapper_json_xml.entiti.user;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersXMLDTO {

    @XmlElement(name = "user")
    private List<UserXMLImportDTO> users;

    public List<UserXMLImportDTO> getUsers() {
        return users;
    }
}
