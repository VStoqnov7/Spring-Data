package com.example.productshop.entities.category;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriesContainerDTO {
    @XmlElement(name = "category")
    List<CategoriesImportDTO> categories;

    public List<CategoriesImportDTO> getCategories() {
        return categories;
    }
}
