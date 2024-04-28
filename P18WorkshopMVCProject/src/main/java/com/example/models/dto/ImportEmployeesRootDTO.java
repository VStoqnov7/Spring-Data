package com.example.models.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportEmployeesRootDTO {

    @XmlElement(name = "employee")
    List<ImportEmployeeDTO> employees;

    public ImportEmployeesRootDTO() {}

    public List<ImportEmployeeDTO> getEmployees() {
        return employees;
    }
}
