package com.example.productshop.entities.product;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsDTO {

    @XmlElement(name = "product")
    private List<ProductImportDTO> products;

    public List<ProductImportDTO> getProducts() {
        return products;
    }
}
