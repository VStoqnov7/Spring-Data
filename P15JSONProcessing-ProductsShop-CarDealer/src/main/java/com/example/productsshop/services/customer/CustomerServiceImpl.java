package com.example.productsshop.services.customer;

import com.example.productsshop.entities.customer.Customer;
import com.example.productsshop.entities.customer.CustomerExportDTO;
import com.example.productsshop.entities.customer.CustomerNameCarsMoneyDTO;
import com.example.productsshop.repositories.CustomerRepository;
import com.google.gson.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


import static com.example.productsshop.enums.OutJsonPaths.CUSTOMERS_TOTAL_SALES;
import static com.example.productsshop.enums.OutJsonPaths.ORDERED_CUSTOMERS;

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
//                .registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
//                    @Override
//                    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
//                        return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
//                    }
//                })
//                .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
//                    @Override
//                    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//                        return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//                    }
//                })
                .registerTypeAdapter(CustomerExportDTO.class, new JsonSerializer<CustomerExportDTO>() {
                    @Override
                    public JsonElement serialize(CustomerExportDTO src, Type typeOfSrc, JsonSerializationContext context) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("Id", src.getId());
                        jsonObject.addProperty("Name", src.getName());
                        jsonObject.addProperty("BirthDate", src.getBirthDate().toString());
                        jsonObject.addProperty("IsYoungDriver", src.isYoungDriver());
                        jsonObject.addProperty("IsYoungDriver", src.isYoungDriver());
                        jsonObject.add("Sales", new JsonArray());          //  Empty array
                        return jsonObject;
                }})
                .create();
    }


    @Override
    public void orderedCustomers() {
        List<Customer> customers = this.customerRepository.findAllByOrderByBirthDate();

        List<Customer> sortedCustomers = customers.stream()
                .sorted(Comparator.comparing(Customer::getBirthDate)
                        .thenComparing(customer -> !customer.isYoungDriver()))
                .collect(Collectors.toList());

        List<CustomerExportDTO> exportDTOs = sortedCustomers.stream()
                .map(customer -> modelMapper.map(customer,CustomerExportDTO.class)).collect(Collectors.toList());

        try (FileWriter writer = new FileWriter(ORDERED_CUSTOMERS)) {
            gson.toJson(exportDTOs, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void exportCustomersWithCars() {
        List<Customer> customersWithCars = customerRepository.findAllBySalesNotNull();

        List<CustomerNameCarsMoneyDTO> exportDTOs = customersWithCars.stream()
                .map(customer -> {
                    CustomerNameCarsMoneyDTO exportDTO = modelMapper.map(customer,CustomerNameCarsMoneyDTO.class);
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

        try (FileWriter writer = new FileWriter(CUSTOMERS_TOTAL_SALES)) {
            gson.toJson(exportDTOs, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
