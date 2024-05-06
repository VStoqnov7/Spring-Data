package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.CustomerImportDTO;
import exam.model.entity.Customer;
import exam.model.entity.Town;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import exam.util.MyValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final String CUSTOMER_PATH = "src/main/resources/files/json/customers.json";
    private final ModelMapper modelMapper;
    private final MyValidator validator;
    private final Gson gson;
    private final CustomerRepository customerRepository;
    private final TownRepository townRepository;

    public CustomerServiceImpl(ModelMapper modelMapper, MyValidator validator, Gson gson, CustomerRepository customerRepository, TownRepository townRepository) {
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.gson = gson;
        this.customerRepository = customerRepository;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(Path.of(CUSTOMER_PATH));
    }

    @Override
    public String importCustomers() throws IOException {
        StringBuilder sb = new StringBuilder();

        CustomerImportDTO[] customerDTO = gson.fromJson(readCustomersFileContent(),CustomerImportDTO[].class);
        Arrays.stream(customerDTO)
                .forEach(dto-> {
                    boolean isValid = validator.isValid(dto);
                    Optional<Customer> byEmail = this.customerRepository.findByEmail(dto.getEmail());
                    if (isValid && !byEmail.isPresent()){
                        Customer customer = modelMapper.map(dto,Customer.class);
                        Optional<Town> town = this.townRepository.findByName(dto.getTown().getName());
                        customer.setTown(town.get());
                        this.customerRepository.save(customer);
                        sb.append(String.format("Successfully imported Customer %s %s - %s",dto.getFirstName(),dto.getLastName(),dto.getEmail())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid Customer").append(System.lineSeparator());
                    }
                });
        return sb.toString().trim();
    }
}
