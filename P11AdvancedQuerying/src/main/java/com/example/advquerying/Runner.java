package com.example.advquerying;

import com.example.advquerying.services.IngredientService;
import com.example.advquerying.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Runner implements CommandLineRunner {
    private final String PRINT_FORMAT_SELECT_SHAMPOOS = "%s %s %.2flv%n";

    private final ShampooService shampooService;


    private final IngredientService ingredientService;

    @Autowired
    public Runner(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);


//        1.	Select Shampoos by Size
//        Size size = Size.valueOf(scanner.nextLine().toUpperCase());
//        this.shampooService.findBySizeOrderBySizeAscIdAsc(size)
//                .forEach(shampoo -> System.out.printf(PRINT_FORMAT_SELECT_SHAMPOOS,shampoo.getBrand(),shampoo.getSize(),shampoo.getPrice()));


//        2.	Select Shampoos by Size or Label
//        Size size = Size.valueOf(scanner.nextLine().toUpperCase());
//        Long labelId = scanner.nextLong();
//        this.shampooService.findAllBySizeOrLabelIdOrderByPrice(size,labelId)
//                .forEach(shampoo -> System.out.printf(PRINT_FORMAT_SELECT_SHAMPOOS,shampoo.getBrand(),shampoo.getSize(),shampoo.getPrice()));


//        3.	Select Shampoos by Price
//        BigDecimal shampooPrice = new BigDecimal(scanner.nextLine());
//        this.shampooService.findByPriceGreaterThanOrderByPriceDesc(shampooPrice)
//                .forEach(shampoo -> System.out.printf(PRINT_FORMAT_SELECT_SHAMPOOS,shampoo.getBrand(),shampoo.getSize(),shampoo.getPrice()));


//        4.	Select Ingredients by Name
//        String name = scanner.nextLine();
//        this.ingredientService.findByNameStartsWith(name).forEach(ingredient -> System.out.println(ingredient.getName()));


//        5.	Select Ingredients by Names
//       List<String> ingredients = new ArrayList<>();
//       String nextLine = scanner.nextLine();
//       while (!nextLine.isBlank()){
//           ingredients.add(nextLine);
//           nextLine = scanner.nextLine();
//       }
//       this.ingredientService.findByNameInOrderByPriceAsc(ingredients).forEach(ingredient -> System.out.println(ingredient.getName()));


//        6.	Count Shampoos by Price
//        BigDecimal price = new BigDecimal(scanner.nextLine());
//        int count = this.shampooService.countByPriceLessThan(price);
//        System.out.println(count);

//        7.	Select Shampoos by Ingredients
//        Set<String> ingredients = new LinkedHashSet<>();
//        String nextLine = scanner.nextLine();
//        while (!nextLine.isBlank()) {
//            ingredients.add(nextLine);
//            nextLine = scanner.nextLine();
//        }
//        Set<String> output = new LinkedHashSet<>();
//        this.shampooService.findByIngredientsNames(ingredients).forEach(shampoo -> output.add(shampoo.getBrand()));
//        output.forEach(System.out::println);


//        8.	Select Shampoos by Ingredients Count
//        int count = Integer.parseInt(scanner.nextLine());
//        this.shampooService.findByIngredientCountLessThan(count).forEach(shampoo -> System.out.println(shampoo.getBrand()));

//        9.	Delete Ingredients by Name
//        String name = scanner.nextLine();
//        int count = this.ingredientService.deleteByName(name);
//        System.out.println(count);


//        10.	Update Ingredients by Price
//        BigDecimal percent = new BigDecimal(scanner.nextLine());
//        this.ingredientService.increasePriceByPercent(percent);

//        11.	Update Ingredients by Names
        BigDecimal percent = new BigDecimal(scanner.nextLine());
        Set<String> ingredients = new LinkedHashSet<>();
        String nextLine = scanner.nextLine();
        while (!nextLine.isBlank()) {
            ingredients.add(nextLine);
            nextLine = scanner.nextLine();
        }
        this.ingredientService.increasePriceByName(percent,ingredients);
    }
}