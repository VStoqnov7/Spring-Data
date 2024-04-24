package com.example.productshop.entities.sale;


import com.example.productshop.entities.car.CarExportSaleDTO;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class SaleExportDTO {

    @XmlElement
    private CarExportSaleDTO car;

    @XmlElement(name = "customer-name")
    private String customerName;

    @XmlElement(name = "discount")
    private double discount;

    @XmlElement
    private double price;

    @XmlElement(name = "price-with-discount")
    private double priceWithDiscount;

    public CarExportSaleDTO getCar() {
        return car;
    }

    public void setCar(CarExportSaleDTO car) {
        this.car = car;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(double priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
