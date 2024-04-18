package com.example.springintro.service;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import com.example.springintro.model.entity.dto.BookInformation;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies);

    List<Book> findByPriceLessThanOrPriceGreaterThan(BigDecimal low, BigDecimal high);

    List<Book> findByReleaseDateYearNot(int year);

    List<Book> findByReleaseDateBefore(LocalDate date);

    List<Book> findByTitleContaining(String name);

    List<Book> findByAuthorLastNameStartsWith(String lastNameStartsWith);

    int findByTitleLength(Integer number);

    int findCountCopiesByAuthorName(String firstName, String lastName);

    BookInformation findFirstByTitle(String title);

    int addCopies(int count, LocalDate date);

    int deleteByCopiesLessThan(int copies);

    int countBooksByAuthorName(String firstName,String lastName);

}
