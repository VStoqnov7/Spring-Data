package com.example.springintro;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.EditionType;
import com.example.springintro.model.entity.dto.BookInformation;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Component
public class UtilsCommandLineRunnerImpl {

    private CategoryService categoryService;
    private AuthorService authorService;
    private BookService bookService;
    private Scanner scanner;

    public UtilsCommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.scanner = new Scanner(System.in);
    }

    public void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }

    //1.	Books Titles by Age Restriction
    public void booksTitlesByAgeRestriction() {
        String ageRestriction = scanner.nextLine().toUpperCase();
        this.bookService.findByAgeRestriction(AgeRestriction.valueOf(ageRestriction)).forEach(book -> System.out.println(book.getTitle()));
    }

    //2.	Golden Books
    public void goldenBooks() {
        this.bookService.findByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000).forEach(book -> System.out.println(book.getTitle()));
    }

    //3.	Books by Price
    public void booksByPrice() {
        this.bookService.findByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(5), BigDecimal.valueOf(40))
                .forEach(book -> System.out.println(book.getTitle() + " - $" + book.getPrice()));
    }

    //4.	Not Released Books
    public void notReleasedBooks() {
        int year = Integer.parseInt(scanner.nextLine());

        this.bookService.findByReleaseDateYearNot(year).forEach(book -> System.out.println(book.getTitle()));
    }

    //    5.	Books Released Before Date
    public void booksReleasedBeforeDate() {
        int[] date = Arrays.stream(scanner.nextLine().split("-")).mapToInt(Integer::parseInt).toArray();
        int year = date[2];
        int month = date[1];
        int day = date[0];

        this.bookService.findByReleaseDateBefore(LocalDate.of(year, month, day))
                .forEach(book -> System.out.println(book.getTitle() + " " + book.getEditionType() + " " + book.getPrice()));
    }

    //    6.	Authors Search
    public void authorsSearch() {
        String name = scanner.nextLine();
        this.authorService.findByFirstNameEndsWith(name).forEach(author -> System.out.println(author.getFirstName() + " " + author.getLastName()));
    }

    //    7.	Books Search
    public void booksSearch() {
        String name = scanner.nextLine();
        this.bookService.findByTitleContaining(name).forEach(book -> System.out.println(book.getTitle()));
    }

    //    8.	Book Titles Search
    public void bookTitlesSearch() {
        String lastNameStartingWith = scanner.nextLine();
        this.bookService.findByAuthorLastNameStartsWith(lastNameStartingWith).forEach(book -> System.out.println(book.getTitle()));

    }

    //    9.	Count Books
    public void countBooks() {
        int number = Integer.parseInt(scanner.nextLine());

        System.out.println(this.bookService.findByTitleLength(number));
    }

    //    10.	Total Book Copies
    public void totalBookCopies() {
        String[] data = scanner.nextLine().split("\\s+");
        String firstName = data[0];
        String lastName = data[1];

        System.out.println(this.bookService.findCountCopiesByAuthorName(firstName, lastName));
    }

    //    11.	Reduced Book
    public void reducedBook() {
        String title = scanner.nextLine();

        BookInformation firstByTitle = this.bookService.findFirstByTitle(title);

        System.out.println(firstByTitle.toString());

    }

    //    12.	* Increase Book Copies
    public void increaseBookCopies() {
        Map<String, String> monthAbbreviations = getStringStringMap();

        String[] date = scanner.nextLine().split("\\s+");
        int year = Integer.parseInt(date[2]);
        int month = Integer.parseInt(monthAbbreviations.get(date[1].toLowerCase()));
        int day = Integer.parseInt(date[0]);

        int copies = Integer.parseInt(scanner.nextLine());

        System.out.println(year);
        System.out.println(month);
        System.out.println(day);

        int count = this.bookService.addCopies(copies, LocalDate.of(year, month, day));
        System.out.println(count * copies);
    }

//      13.	    * Remove Books
    public void removeBooks(){
        int copies = Integer.parseInt(scanner.nextLine());
        System.out.println(this.bookService.deleteByCopiesLessThan(copies));
    }

//      14.	    * Stored Procedure

    public void storedProcedure(){
        String[] data = scanner.nextLine().split("\\s+");
        String firstName = data[0];
        String lastName = data[1];
        System.out.println(this.bookService.countBooksByAuthorName(firstName, lastName));
    }

    private static Map<String, String> getStringStringMap() {
        Map<String, String> monthAbbreviations = new LinkedHashMap<>();
        monthAbbreviations.put("jan", "1");
        monthAbbreviations.put("feb", "2");
        monthAbbreviations.put("mar", "3");
        monthAbbreviations.put("apr", "4");
        monthAbbreviations.put("may", "5");
        monthAbbreviations.put("jun", "6");
        monthAbbreviations.put("jul", "7");
        monthAbbreviations.put("aug", "8");
        monthAbbreviations.put("sep", "9");
        monthAbbreviations.put("oct", "10");
        monthAbbreviations.put("nov", "11");
        monthAbbreviations.put("dec", "12");
        return monthAbbreviations;
    }
}


