package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import com.example.springintro.model.entity.dto.BookInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAgeRestriction(AgeRestriction name);

    List<Book> findByEditionTypeAndCopiesLessThan(EditionType editionType,Integer copies);

    List<Book> findByPriceLessThanOrPriceGreaterThan(BigDecimal low, BigDecimal high);

    @Query("SELECT b FROM Book b WHERE YEAR(b.releaseDate) <> :year")
    List<Book> findByReleaseDateYearNot(int year);

    List<Book> findByReleaseDateBefore(LocalDate date);

    List<Book> findByTitleContaining(String name);

    List<Book> findByAuthorLastNameStartsWith(String lastNameStartsWith);

    @Query("SELECT COUNT(b) FROM Book b WHERE LENGTH(b.title) > :number")
    int findByTitleLength(Integer number);

    @Query("SELECT COUNT(b) FROM Book b JOIN b.author a WHERE a.firstName = :firstName AND a.lastName = :lastName")
    int findCountCopiesByAuthorName(String firstName, String lastName);

    @Query("SELECT new com.example.springintro.model.entity.dto.BookInformation(b.title, b.editionType, b.ageRestriction, b.price) " +
            " FROM Book b WHERE b.title = :title")
    BookInformation findFirstByTitle(String title);

    @Modifying
    @Transactional
    @Query("UPDATE Book SET copies = copies + :count WHERE releaseDate > :date")
    int addCopies(int count, LocalDate date);

    @Transactional
    int deleteByCopiesLessThan(int copies);

    @Procedure(procedureName = "udp_get_total_books_by_author")
    int countBooksByAuthorName(String firstName, String lastName);

    /** This is the procedure in MySQL */

    //DELIMITER $$
    //
    //CREATE PROCEDURE udp_get_total_books_by_author(
    //    IN authorFirstName VARCHAR(255),
    //    IN authorLastName VARCHAR(255),
    //    OUT totalBooks INT
    //)
    //BEGIN
    //    SELECT COUNT(*)
    //    INTO totalBooks
    //    FROM books AS b
    //    JOIN authors AS a ON b.author_id = a.id
    //    WHERE a.first_name = authorFirstName
    //    AND a.last_name = authorLastName;
    //END$$
    //
    //DELIMITER ;
}
