package com.example.productshop.entities.category;

import jakarta.xml.bind.annotation.*;

import java.math.BigDecimal;
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryByProductCountDTO {

    @XmlAttribute(name = "name")
    private String name;
    @XmlElement(name = "product-count")
    private Long count;

    @XmlElement(name = "average-price")
    private double averagePrice;

    @XmlElement(name = "total-revenue")
    private BigDecimal totalRevenue;

    public CategoryByProductCountDTO(String name, Long count, double averagePrice, BigDecimal totalRevenue) {
        this.name = name;
        this.count = count;
        this.averagePrice = averagePrice;
        this.totalRevenue = totalRevenue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
