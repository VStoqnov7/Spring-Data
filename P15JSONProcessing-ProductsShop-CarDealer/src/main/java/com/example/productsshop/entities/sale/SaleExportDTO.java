package com.example.productsshop.entities.sale;


import com.example.productsshop.entities.car.Car;
import com.example.productsshop.entities.car.CarInfoDTO;
import com.google.gson.annotations.SerializedName;

public class SaleExportDTO {
    private CarInfoDTO car;

    @SerializedName("Customer Name")
    private String customerName;

    @SerializedName("Discount")
    private double discount;

    private double price;
    private double priceWithDiscount;

    public CarInfoDTO getCar() {
        return car;
    }

    public void setCar(CarInfoDTO car) {
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
