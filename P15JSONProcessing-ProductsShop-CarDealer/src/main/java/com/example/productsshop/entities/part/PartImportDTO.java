package com.example.productsshop.entities.part;

import com.example.productsshop.entities.supplier.Supplier;
import jakarta.persistence.ManyToOne;

public class PartImportDTO {

    private String name;
    private double price;
    private int quantity;

    private Supplier supplier;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
