package com.example.productshop.entities.product;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProductNameAndPriceDTO {

    @XmlAttribute
    private String name;

    @XmlAttribute
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
