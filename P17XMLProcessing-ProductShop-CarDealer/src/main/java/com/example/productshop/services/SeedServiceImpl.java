package com.example.productshop.services;

import com.example.productshop.entities.car.Car;
import com.example.productshop.entities.car.CarImportContainerDTO;
import com.example.productshop.entities.category.CategoriesContainerDTO;
import com.example.productshop.entities.category.Category;
import com.example.productshop.entities.customer.Customer;
import com.example.productshop.entities.customer.CustomerImportContainerDTO;
import com.example.productshop.entities.part.Part;
import com.example.productshop.entities.part.PartImportContainerDTO;
import com.example.productshop.entities.product.Product;
import com.example.productshop.entities.product.ProductsDTO;
import com.example.productshop.entities.sale.Sale;
import com.example.productshop.entities.supplier.Supplier;
import com.example.productshop.entities.user.User;
import com.example.productshop.entities.user.UsersDTO;
import com.example.productshop.repositories.*;
import com.example.productshop.entities.supplier.SupplierImportContainerDTO;
import com.google.gson.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.productshop.enums.ReadXmlPaths.*;


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
    public void seedUsers() throws JAXBException {
        if (userRepository.count() == 0) {
            JAXBContext context = JAXBContext.newInstance(UsersDTO.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            try (FileReader fileReader = new FileReader(USERS_XML_PATH)) {
                UsersDTO usersContainerDTO = (UsersDTO) unmarshaller.unmarshal(fileReader);

                List<User> users = usersContainerDTO.getUsers().stream()
                        .map(dto -> modelMapper.map(dto, User.class))
                        .collect(Collectors.toList());

                userRepository.saveAllAndFlush(users);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void seedProducts() throws JAXBException, FileNotFoundException {
        if (this.productRepository.count() == 0) {
            JAXBContext context = JAXBContext.newInstance(ProductsDTO.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            try (FileReader fileReader = new FileReader(PRODUCTS_XML_PATH)) {
                ProductsDTO productsContainerDto = (ProductsDTO) unmarshaller.unmarshal(fileReader);

                List<Product> products = productsContainerDto.getProducts().stream()
                        .map(dto -> modelMapper.map(dto, Product.class))
                        .map(this::setRandomBuyer)
                        .map(this::setRandomSeller)
                        .map(this::setRandomCategories)
                        .collect(Collectors.toList());
                productRepository.saveAllAndFlush(products);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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

    @Override
    public void seedCategories() throws JAXBException, FileNotFoundException {
        if (this.categoryRepository.count() == 0){
            JAXBContext context = JAXBContext.newInstance(CategoriesContainerDTO.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            try(FileReader fileReader = new FileReader(CATEGORIES_XML_PATH)){
                CategoriesContainerDTO categoriesContainerDTO = (CategoriesContainerDTO) unmarshaller.unmarshal(fileReader);

                List<Category> categories = categoriesContainerDTO.getCategories().stream()
                        .map(dto -> modelMapper.map(dto, Category.class))
                        .collect(Collectors.toList());

                this.categoryRepository.saveAllAndFlush(categories);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void seedSuppliers() throws JAXBException, IOException {
        if (this.supplierRepository.count() == 0){
            JAXBContext context = JAXBContext.newInstance(SupplierImportContainerDTO.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            try(FileReader fileReader = new FileReader(SUPPLIERS_XML_PATH)){
                SupplierImportContainerDTO supplierContainerDTO = (SupplierImportContainerDTO) unmarshaller.unmarshal(fileReader);

                List<Supplier> suppliers = supplierContainerDTO.getSuppliers().stream()
                        .map(dto -> modelMapper.map(dto,Supplier.class))
                        .collect(Collectors.toList());

                this.supplierRepository.saveAllAndFlush(suppliers);
            }
        }
    }

    @Override
    public void seedParts() throws JAXBException, IOException {
        if (partRepository.count() == 0){
            JAXBContext context = JAXBContext.newInstance(PartImportContainerDTO.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            try(FileReader fileReader = new FileReader(PARTS_XML_PATH)){
                PartImportContainerDTO partContainerDTO = (PartImportContainerDTO) unmarshaller.unmarshal(fileReader);

                List<Part> parts = partContainerDTO.getParts().stream()
                        .map(dto-> modelMapper.map(dto,Part.class))
                        .map(this::setRandomSupplier)
                        .collect(Collectors.toList());

                this.partRepository.saveAllAndFlush(parts);
            }
        }
    }

    @Override
    public void seedCars() throws JAXBException, IOException {
        if (carRepository.count() == 0){
            JAXBContext context = JAXBContext.newInstance(CarImportContainerDTO.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            try(FileReader fileReader = new FileReader(CARS_XML_PATH)){
                CarImportContainerDTO carContainerDTO = (CarImportContainerDTO) unmarshaller.unmarshal(fileReader);
                List<Car> cars = carContainerDTO.getCars().stream()
                        .map(dto-> modelMapper.map(dto,Car.class))
                        .map(this::setRandomParts)
                        .collect(Collectors.toList());

                this.carRepository.saveAllAndFlush(cars);
            }
        }
    }

    @Override
    public void seedCustomers() throws JAXBException, IOException {
        if (this.customerRepository.count() == 0){
            JAXBContext context = JAXBContext.newInstance(CustomerImportContainerDTO.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //  fixed with LocalDateTimeAdapter.class

            try(FileReader fileReader = new FileReader(CUSTOMERS_XML_PATH)){
                CustomerImportContainerDTO customerContainerDTO = (CustomerImportContainerDTO) unmarshaller.unmarshal(fileReader);

                List<Customer> customers = customerContainerDTO.getCustomers().stream()
                        .map(dto-> modelMapper.map(dto,Customer.class))
                        .collect(Collectors.toList());

                this.customerRepository.saveAllAndFlush(customers);
            }
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

}
