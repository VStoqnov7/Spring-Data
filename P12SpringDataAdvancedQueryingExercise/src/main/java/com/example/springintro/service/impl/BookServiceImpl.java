package com.example.springintro.service.impl;

import com.example.springintro.model.entity.*;
import com.example.springintro.model.entity.dto.BookInformation;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findByAgeRestriction(AgeRestriction ageRestriction) {
        return this.bookRepository.findByAgeRestriction(ageRestriction);
    }

    @Override
    public List<Book> findByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies) {
        return this.bookRepository.findByEditionTypeAndCopiesLessThan(editionType,copies);
    }

    @Override
    public List<Book> findByPriceLessThanOrPriceGreaterThan(BigDecimal low, BigDecimal high) {
        return this.bookRepository.findByPriceLessThanOrPriceGreaterThan(low,high);
    }

    @Override
    public List<Book> findByReleaseDateYearNot(int year) {
        return this.bookRepository.findByReleaseDateYearNot(year);
    }

    @Override
    public List<Book> findByReleaseDateBefore(LocalDate date) {
        return this.bookRepository.findByReleaseDateBefore(date);
    }

    @Override
    public List<Book> findByTitleContaining(String name) {
        return this.bookRepository.findByTitleContaining(name);
    }

    @Override
    public List<Book> findByAuthorLastNameStartsWith(String lastNameStartsWith) {
        return this.bookRepository.findByAuthorLastNameStartsWith(lastNameStartsWith);
    }

    @Override
    public int findByTitleLength(Integer number) {
        return this.bookRepository.findByTitleLength(number);
    }

    @Override
    public int findCountCopiesByAuthorName(String firstName, String lastName) {
        return this.bookRepository.findCountCopiesByAuthorName(firstName,lastName);
    }

    @Override
    public BookInformation findFirstByTitle(String title) {
        return this.bookRepository.findFirstByTitle(title);
    }

    @Override
    public int addCopies(int count, LocalDate date) {
        return this.bookRepository.addCopies(count,date);
    }

    @Override
    public int deleteByCopiesLessThan(int copies) {
        return this.bookRepository.deleteByCopiesLessThan(copies);
    }

    @Override
    public int countBooksByAuthorName(String firstName, String lastName) {
        return this.bookRepository.countBooksByAuthorName(firstName,lastName);
    }


    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));
        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();
        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);
    }
}
