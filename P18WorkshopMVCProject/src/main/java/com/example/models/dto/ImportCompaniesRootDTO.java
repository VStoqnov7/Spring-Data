package com.example.models.dto;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportCompaniesRootDTO {

    @XmlElement(name = "company")
    List<ImportCompanyDTO> companies;

    public ImportCompaniesRootDTO() {}

    public List<ImportCompanyDTO> getCompanies() {
        return companies;
    }
}