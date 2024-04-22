package com.example.productsshop.services.sale;


import com.example.productsshop.entities.car.CarInfoDTO;
import com.example.productsshop.entities.sale.Sale;
import com.example.productsshop.entities.sale.SaleExportDTO;
import com.example.productsshop.repositories.SaleRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.productsshop.enums.OutJsonPaths.SALES_DISCOUNTS;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void exportSalesWithDiscounts() {
        List<Sale> sales = saleRepository.findAll();
        List<SaleExportDTO> saleExportDTOs = sales.stream()
                .map(sale -> {
                    SaleExportDTO saleExportDTO = modelMapper.map(sale, SaleExportDTO.class);
                    CarInfoDTO carInfoDTO = modelMapper.map(sale.getCar(), CarInfoDTO.class);
                    saleExportDTO.setCar(carInfoDTO);
                    saleExportDTO.setCustomerName(sale.getCustomer().getName());
                    double totalPriceWithoutDiscount = sale.getCar().getParts().stream()
                            .mapToDouble(part -> part.getPrice())
                            .sum();
                    saleExportDTO.setPrice(totalPriceWithoutDiscount);
                    double discountPercentage = sale.getDiscountPercentage() / 100.0;
                    double priceWithDiscount = totalPriceWithoutDiscount * (1 - discountPercentage);
                    saleExportDTO.setPriceWithDiscount(priceWithDiscount);
                    saleExportDTO.setDiscount(discountPercentage);
                    return saleExportDTO;
                })
                .collect(Collectors.toList());

        try (FileWriter writer = new FileWriter(SALES_DISCOUNTS)) {
            gson.toJson(saleExportDTOs, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
