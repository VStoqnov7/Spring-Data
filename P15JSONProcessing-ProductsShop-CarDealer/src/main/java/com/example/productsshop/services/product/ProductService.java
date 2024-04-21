package com.example.productsshop.services.product;

import java.io.IOException;
import java.math.BigDecimal;

public interface ProductService {
    void getProductsInRangeWithNoBuyer(BigDecimal minPrice, BigDecimal maxPrice) throws IOException;
}
