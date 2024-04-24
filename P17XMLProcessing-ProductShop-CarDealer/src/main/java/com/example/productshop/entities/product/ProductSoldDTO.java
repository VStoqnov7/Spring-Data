package com.example.productshop.entities.product;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSoldDTO {

    @XmlAttribute(name = "count")
    private int count;

    @XmlElement(name = "product")
    private List<ProductNameAndPriceDTO> soldProducts;


    public List<ProductNameAndPriceDTO> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductNameAndPriceDTO> soldProducts) {
        this.soldProducts = soldProducts;
        this.count = this.soldProducts.size();
    }
}
