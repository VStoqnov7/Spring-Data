package com.example.productshop.services.customer;


import com.example.productshop.entities.category.CategoryByProductCountContainerDTO;
import com.example.productshop.entities.category.CategoryByProductCountDTO;
import com.example.productshop.entities.customer.*;
import com.example.productshop.entities.product.Product;
import com.example.productshop.entities.product.ProductsInRangeContainerDTO;
import com.example.productshop.entities.product.ProductsInRangeExportDTO;
import com.example.productshop.repositories.CustomerRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.productshop.enums.OutXmlPaths.*;

@Service
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    private final Gson gson;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }


    @Override
    public void orderedCustomers() throws JAXBException, IOException {
        List<Customer> customers = this.customerRepository.findAllByOrderByBirthDate();
        List<CustomerExportDTO> sortedCustomers = customers.stream()
                .map(customer-> modelMapper.map(customer, CustomerExportDTO.class))
                .sorted(Comparator.comparing(customer -> !customer.isYoungDriver()))
                .collect(Collectors.toList());

        CustomerExportContainerDTO containerDTO = new CustomerExportContainerDTO();
        containerDTO.setCustomers(sortedCustomers);
        JAXBContext content = JAXBContext.newInstance(CustomerExportContainerDTO.class);
        Marshaller marshaller = content.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        //  fixed with LocalDateTimeAdapter.class
        try(FileWriter writer = new FileWriter(ORDERED_CUSTOMERS)){
            marshaller.marshal(containerDTO,writer);
        }

    }

    @Override
    public void exportCustomersWithCars() throws JAXBException, IOException {
        List<Customer> customers = this.customerRepository.findAllBySalesNotNull();

        List<CustomerTotalSalesExportDTO> customerTotalSalesExportDTOS = customers.stream()
                .map(customer -> {
                    CustomerTotalSalesExportDTO exportDTO = modelMapper.map(customer,CustomerTotalSalesExportDTO.class);
                    exportDTO.setFullName(customer.getName());
                    exportDTO.setBoughtCars(customer.getSales().size());
                    exportDTO.setSpentMoney(customer.getSales().stream()
                            .mapToDouble(sale -> sale.getCar().getParts().stream()
                                    .mapToDouble(part -> part.getPrice())
                                    .sum())
                            .sum());
                    return exportDTO;
                })
                .collect(Collectors.toList());

        CustomerTotalSalesExportContainerDTO customerContainerDTO = new CustomerTotalSalesExportContainerDTO();
        customerContainerDTO.setCustomers(customerTotalSalesExportDTOS);

        JAXBContext context = JAXBContext.newInstance(CustomerTotalSalesExportContainerDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        try(FileWriter writer = new FileWriter(CUSTOMERS_TOTAL_SALES)){
            marshaller.marshal(customerContainerDTO,writer);
        }

    }
}
