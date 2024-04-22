package com.example.productsshop.services;

import com.example.productsshop.entities.car.Car;
import com.example.productsshop.entities.car.CarsImportDTO;
import com.example.productsshop.entities.category.Category;
import com.example.productsshop.entities.category.CategoryImportDTO;
import com.example.productsshop.entities.customer.Customer;
import com.example.productsshop.entities.customer.CustomerImportDTO;
import com.example.productsshop.entities.part.Part;
import com.example.productsshop.entities.part.PartImportDTO;
import com.example.productsshop.entities.product.Product;
import com.example.productsshop.entities.product.ProductImportDTO;
import com.example.productsshop.entities.sale.Sale;
import com.example.productsshop.entities.supplier.Supplier;
import com.example.productsshop.entities.supplier.SupplierImportDTO;
import com.example.productsshop.entities.user.User;
import com.example.productsshop.entities.user.UserImportDTO;
import com.example.productsshop.repositories.*;
import com.google.gson.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.productsshop.enums.ReadJsonPaths.*;

@Service
public class SeedServiceImpl implements SeedService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final PartRepository partRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;

    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository,
                           CategoryRepository categoryRepository,
                           ProductRepository productRepository, SupplierRepository supplierRepository, PartRepository partRepository, CarRepository carRepository, CustomerRepository customerRepository, SaleRepository saleRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.partRepository = partRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;

        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                    @Override
                    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
                    }
                })
                .create();
    }


    @Override
    public void seedUsers() throws FileNotFoundException {
        if (userRepository.count() == 0) {
            FileReader reedUsers = new FileReader(USERS_JSON_PATH);
            UserImportDTO[] userImportDTOS = this.gson.fromJson(reedUsers, UserImportDTO[].class);

            List<User> users = Arrays.stream(userImportDTOS)
                    .map(dto -> this.modelMapper.map(dto, User.class))
                    .collect(Collectors.toList());


            this.userRepository.saveAllAndFlush(users);
        }
    }

    @Override
    public void seedCategories() throws FileNotFoundException {
        if (categoryRepository.count() == 0) {
            FileReader reedCategories = new FileReader(CATEGORIES_JSON_PATH);
            CategoryImportDTO[] categoryImportDTO = this.gson.fromJson(reedCategories, CategoryImportDTO[].class);

            List<Category> categories = Arrays.stream(categoryImportDTO)
                    .map(dto -> this.modelMapper.map(dto, Category.class))
                    .collect(Collectors.toList());

            this.categoryRepository.saveAllAndFlush(categories);
        }

    }

    @Override
    public void seedSuppliers() throws FileNotFoundException {
        if (supplierRepository.count() == 0) {
            FileReader reedSuppliers = new FileReader(SUPPLIERS_JSON_PATH);
            SupplierImportDTO[] supplierImportDTOS = this.gson.fromJson(reedSuppliers, SupplierImportDTO[].class);

            List<Supplier> suppliers = Arrays.stream(supplierImportDTOS)
                    .map(dto -> this.modelMapper.map(dto, Supplier.class))
                    .collect(Collectors.toList());
            this.supplierRepository.saveAllAndFlush(suppliers);
        }
    }

    @Override
    public void seedParts() throws FileNotFoundException {
        if (partRepository.count() == 0) {
            FileReader reedParts = new FileReader(PARTS_JSON_PATH);
            PartImportDTO[] partImportDTOS = gson.fromJson(reedParts, PartImportDTO[].class);

            List<Part> parts = Arrays.stream(partImportDTOS)
                    .map(dto -> modelMapper.map(dto, Part.class))
                    .map(this::setRandomSupplier)
                    .collect(Collectors.toList());
            this.partRepository.saveAllAndFlush(parts);
        }
    }

    @Override
    public void seedCars() throws FileNotFoundException {

        if (carRepository.count() == 0) {
            FileReader reedCars = new FileReader(CARS_JSON_PATH);
            CarsImportDTO[] carsImportDTOS = gson.fromJson(reedCars, CarsImportDTO[].class);
            List<Car> cars = Arrays.stream(carsImportDTOS)
                    .map(dto -> modelMapper.map(dto, Car.class))
                    .map(this::setRandomParts)
                    .collect(Collectors.toList());
            this.carRepository.saveAllAndFlush(cars);
        }
    }

    @Override
    public void seedCustomers() throws FileNotFoundException {
        if (customerRepository.count() == 0) {
            FileReader reedCustomers = new FileReader(CUSTOMERS_JSON_PATH);
            CustomerImportDTO[] customerImportDTOS = gson.fromJson(reedCustomers, CustomerImportDTO[].class);
            List<Customer> customers = Arrays.stream(customerImportDTOS)
                    .map(dto -> modelMapper.map(dto, Customer.class))
                    .collect(Collectors.toList());

            this.customerRepository.saveAllAndFlush(customers);
        }
    }

    @Override
    public void seedSales() {
        if (saleRepository.count() == 0) {
            Random random = new Random();
            List<Car> cars = carRepository.findAll();
            List<Customer> customers = customerRepository.findAll();
            List<Integer> percentages = Arrays.asList(0, 5, 10, 15, 20, 30, 40, 50);
            for (int i = 0; i < 100; i++) {
                Car car = cars.get(random.nextInt(cars.size()));
                Customer customer = customers.get(random.nextInt(customers.size()));
                int percent = percentages.get(random.nextInt(percentages.size()));
                Sale sale = new Sale();
                sale.setCar(car);
                sale.setCustomer(customer);
                sale.setDiscountPercentage(percent);
                saleRepository.save(sale);
            }
        }
    }

    private class LocalDateAdapter implements JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return LocalDate.parse(json.getAsString());
        }
    }

    private Car setRandomParts(Car car) {
        List<Part> parts = this.partRepository.findAll();
        if (!parts.isEmpty()) {
            int numberOfPartsToAdd = ThreadLocalRandom.current().nextInt(3, 6);

            List<Part> randomParts = new ArrayList<>();
            for (int i = 0; i < numberOfPartsToAdd; i++) {
                int randomIndex = ThreadLocalRandom.current().nextInt(parts.size());
                Part randomPart = parts.get(randomIndex);
                randomParts.add(randomPart);
                parts.remove(randomIndex);   // Delete the part from the list so that it is not selected again
            }

            car.setParts(randomParts);
        }
        return car;
    }

    private Part setRandomSupplier(Part part) {
        List<Supplier> suppliers = this.supplierRepository.findAll();
        if (!suppliers.isEmpty()) {
            Random random = new Random();
            Supplier supplier = suppliers.get(random.nextInt(suppliers.size()));
            part.setSupplier(supplier);
        }
        return part;
    }

    @Override
    public void seedProducts() throws FileNotFoundException {
        if (productRepository.count() == 0) {
            FileReader reedProducts = new FileReader(PRODUCTS_JSON_PATH);
            ProductImportDTO[] productImportDTO = this.gson.fromJson(reedProducts, ProductImportDTO[].class);

            List<Product> products = Arrays.stream(productImportDTO)
                    .map(dto -> this.modelMapper.map(dto, Product.class))
                    .map(this::setRandomSeller)
                    .map(this::setRandomBuyer)
                    .map(this::setRandomCategories)
                    .collect(Collectors.toList());

            this.productRepository.saveAll(products);
        }
    }


    private Product setRandomCategories(Product product) {
        List<Category> categories = this.categoryRepository.findAll();
        if (!categories.isEmpty()) {
            Random random = new Random();
//            int numCategories = random.nextInt(categories.size()) + 1;
            for (int i = 0; i < categories.size(); i++) {                //for (int i = 0; i < numCategories; i++) {
                Category randomCategory = categories.get(random.nextInt(categories.size()));
                product.getCategories().add(randomCategory);
            }
        }
        return product;
    }

    private Product setRandomBuyer(Product product) {
        List<User> buyers = this.userRepository.findAll();
        if (!buyers.isEmpty()) {
            Random random = new Random();
            if (random.nextInt(buyers.size()) != 5 && random.nextInt(buyers.size()) != 9 && random.nextInt(buyers.size()) != 10
                    && random.nextInt(buyers.size()) != 15 && random.nextInt(buyers.size()) != 20) {
                User randomBuyer = buyers.get(random.nextInt(buyers.size()));
                product.setBuyer(randomBuyer);
            }
        }
        return product;
    }

    private Product setRandomSeller(Product product) {
        List<User> sellers = this.userRepository.findAll();
        if (!sellers.isEmpty()) {
            Random random = new Random();
            User randomSeller = sellers.get(random.nextInt(sellers.size()));
            product.setSeller(randomSeller);
        }
        return product;

    }
}
