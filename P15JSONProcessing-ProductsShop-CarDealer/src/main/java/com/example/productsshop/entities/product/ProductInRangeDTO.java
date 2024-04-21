package com.example.productsshop.entities.product;

import com.example.productsshop.entities.user.User;


import java.math.BigDecimal;

public class ProductInRangeDTO {

    private String name;
    private BigDecimal price;
    private String seller;

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

    public String getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller.getFirstName() + " " + seller.getLastName();
    }
}
