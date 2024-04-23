package com.example.modelmapper_json_xml.services;

import com.example.modelmapper_json_xml.entiti.*;
import com.example.modelmapper_json_xml.entiti.car.CarInfoDTO;
import com.example.modelmapper_json_xml.entiti.sale.Sale;
import com.example.modelmapper_json_xml.entiti.sale.SaleExportContainerDTO;
import com.example.modelmapper_json_xml.entiti.sale.SaleExportXmlDTO;
import com.example.modelmapper_json_xml.entiti.user.UserJSONImportDTO;
import com.example.modelmapper_json_xml.repositories.SaleRepository;
import com.google.gson.Gson;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.modelmapper_json_xml.enums.OutJsonPaths.SALES_DISCOUNTS;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    private final ModelMapper modelMapper;
    private final Gson gson;

    public SaleService(SaleRepository saleRepository, /*@Qualifier("име на been ")*/ ModelMapper modelMapper, /*@Qualifier("име на been ")*/ Gson gson) {
        this.saleRepository = saleRepository;
        this.modelMapper = modelMapper;                     // Implements in Configuration !
        this.gson = gson;                                   // Implements in Configuration !
    }

    public void exportSalesWithDiscountsJson() {
        List<Sale> sales = saleRepository.findAll();
        List<SaleExportJsonDTO> saleExportDTOs = sales.stream()
                .map(sale -> {
                    SaleExportJsonDTO saleExportDTO = modelMapper.map(sale, SaleExportJsonDTO.class);
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

    public void exportSalesWithDiscountXml() throws JAXBException, IOException {
        List<Sale> sales = this.saleRepository.findAll();
        List<SaleExportXmlDTO> saleExportDTOS = sales.stream()
                .map(sale ->{
                    SaleExportXmlDTO saleExportDTO = modelMapper.map(sale,SaleExportXmlDTO.class);
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



    public void exportUser(){
        UserJSONImportDTO userImportDTO = new UserJSONImportDTO();
        userImportDTO.setFirstName("John");
        userImportDTO.setAge(30);

        try {
            JAXBContext context = JAXBContext.newInstance(UserJSONImportDTO.class); // Създаване на JAXB контекст за обекта UserImportDTO
            Marshaller marshaller = context.createMarshaller();               // Създаване на Marshaller
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  // Настройка на Marshaller за форматиране на изхода (може да се пропусне)

            FileWriter writer = new FileWriter("src\\......user.xml");  // Създаване на FileWriter за запис на файл

            marshaller.marshal(userImportDTO, writer);    // Запис на обекта към файл чрез FileWriter

            writer.close();     // Затваряне на FileWriter

            System.out.println("Обектът е успешно записан във файл.");
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }
}
