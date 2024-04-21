package com.example.productsshop.services.product;

import com.example.productsshop.entities.product.Product;
import com.example.productsshop.entities.product.ProductInRangeDTO;
import com.example.productsshop.repositories.ProductRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.productsshop.enums.OutJsonPaths.PRODUCTS_IN_RANGE;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.modelMapper =  new ModelMapper();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void getProductsInRangeWithNoBuyer(BigDecimal minPrice, BigDecimal maxPrice) throws IOException {
        List<Product> products = productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(minPrice, maxPrice);

        List<ProductInRangeDTO> productsDTO = products.stream()
                .map(product -> modelMapper.map(product, ProductInRangeDTO.class))
                .collect(Collectors.toList());

        try (FileWriter writer = new FileWriter(PRODUCTS_IN_RANGE)) {
            gson.toJson(productsDTO, writer);
        }
    }
}
