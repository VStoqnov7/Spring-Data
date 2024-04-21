package com.example.productsshop.entities.product;

import java.util.List;

public class SoldProductsCountDTO {

    private int count;

    private List<ProductsNameAndPriceDTO> products;



    public List<ProductsNameAndPriceDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsNameAndPriceDTO> products) {
        this.products = products;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
