package com.example.productshop.services.product;

import com.example.productshop.entities.product.Product;
import com.example.productshop.entities.product.ProductsInRangeContainerDTO;
import com.example.productshop.entities.product.ProductsInRangeExportDTO;
import com.example.productshop.repositories.ProductRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.productshop.enums.OutXmlPaths.PRODUCTS_IN_RANGE;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        modelMapper.addMappings(new PropertyMap<Product, ProductsInRangeExportDTO>() {
            protected void configure() {
                map().setSeller(source.getSeller());          //seller="Christine Gomez"
            }
        });
    }

    @Override
    public void getProductsInRangeWithNoBuyer(BigDecimal minPrice, BigDecimal maxPrice) throws IOException, JAXBException {
        List<Product> products = this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(minPrice, maxPrice);

        List<ProductsInRangeExportDTO> productsInRangeDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductsInRangeExportDTO.class))
                .collect(Collectors.toList());

        ProductsInRangeContainerDTO containerDTO = new ProductsInRangeContainerDTO();
        containerDTO.setProducts(productsInRangeDTOS);

        JAXBContext context = JAXBContext.newInstance(ProductsInRangeContainerDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        try (FileWriter writer = new FileWriter(PRODUCTS_IN_RANGE)) {
            marshaller.marshal(containerDTO, writer);
        }

    }
}
