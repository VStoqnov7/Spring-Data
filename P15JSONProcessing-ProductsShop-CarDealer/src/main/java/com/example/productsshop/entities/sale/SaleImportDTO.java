package com.example.productsshop.entities.sale;

import com.example.productsshop.entities.car.Car;
import com.example.productsshop.entities.customer.Customer;

public class SaleImportDTO {
    private Car car;

    private Customer customer;

    private int discountPercentage;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
