package com.example.productshop.entities.category;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryByProductCountContainerDTO {

    @XmlElement(name = "category")
    private List<CategoryByProductCountDTO> categories;

    public List<CategoryByProductCountDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryByProductCountDTO> categories) {
        this.categories = categories;
    }
}
