package com.example.productshop.entities.product;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name ="products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsInRangeContainerDTO {


    @XmlElement(name = "product")
    List<ProductsInRangeExportDTO> products;

    public List<ProductsInRangeExportDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsInRangeExportDTO> products) {
        this.products = products;
    }
}
