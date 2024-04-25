package com.example.productshop;

import com.example.productshop.services.SeedService;
import com.example.productshop.services.car.CarService;
import com.example.productshop.services.category.CategoryService;
import com.example.productshop.services.customer.CustomerService;
import com.example.productshop.services.product.ProductService;
import com.example.productshop.services.sale.SaleService;
import com.example.productshop.services.supplier.SupplierService;
import com.example.productshop.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductShopRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final ProductService productService;

    private final UserService userService;

    private final CategoryService categoryService;
    private final CustomerService customerService;

    private final CarService carService;

    private final SupplierService supplierService;

    private final SaleService saleService;

    @Autowired
    public ProductShopRunner(SeedService seedService, ProductService productService, UserService userService, CategoryService categoryService, CustomerService customerService, CarService carService, SupplierService supplierService, SaleService saleService) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.customerService = customerService;
        this.carService = carService;
        this.supplierService = supplierService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedService.seedAll();
//        this.productService.getProductsInRangeWithNoBuyer(BigDecimal.valueOf(500),BigDecimal.valueOf(1000));
//        this.userService.getUsersWithSoldProducts();
//        this.categoryService.categoriesByProductsCount();
//        this.userService.getAllUsersAndProducts();
//        this.customerService.orderedCustomers();
//        this.carService.exportToyotaCars();
//        this.supplierService.exportSuppliers();
//        this.carService.exportAllCarsAndParts();
//        this.customerService.exportCustomersWithCars();
        this.saleService.exportSalesWithDiscounts();
    }
}
