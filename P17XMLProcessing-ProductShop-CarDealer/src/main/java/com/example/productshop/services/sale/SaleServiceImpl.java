package com.example.productshop.services.sale;

import com.example.productshop.entities.sale.Sale;
import com.example.productshop.entities.sale.SaleExportContainerDTO;
import com.example.productshop.entities.sale.SaleExportDTO;
import com.example.productshop.repositories.SaleRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.stream.Collectors;

import static com.example.productshop.enums.OutXmlPaths.SALES_DISCOUNTS;

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
    public void exportSalesWithDiscounts() throws JAXBException, IOException {
        List<Sale> sales = this.saleRepository.findAll();
        List<SaleExportDTO> saleExportDTOS = sales.stream()
                .map(sale ->{
                    SaleExportDTO saleExportDTO = modelMapper.map(sale,SaleExportDTO.class);
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

        SaleExportContainerDTO saleContainerDTO = new SaleExportContainerDTO();
        saleContainerDTO.setSales(saleExportDTOS);

        JAXBContext context = JAXBContext.newInstance(SaleExportContainerDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        try(FileWriter writer = new FileWriter(SALES_DISCOUNTS)){
            marshaller.marshal(saleContainerDTO,writer);
        }
    }
}
