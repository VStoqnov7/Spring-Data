package com.example.springintro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private UtilsCommandLineRunnerImpl utilsCommandLineRunner;

    public CommandLineRunnerImpl(UtilsCommandLineRunnerImpl utilsCommandLineRunner) {
        this.utilsCommandLineRunner = utilsCommandLineRunner;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.utilsCommandLineRunner.seedData();


//        1.	Books Titles by Age Restriction
//        this.utilsCommandLineRunner.booksTitlesByAgeRestriction();


//        2.	Golden Books
//        this.utilsCommandLineRunner.goldenBooks();

//        3.	Books by Price
//        this.utilsCommandLineRunner.booksByPrice();

//        4.	Not Released Books
//        this.utilsCommandLineRunner.notReleasedBooks();

//        5.	Books Released Before Date
//        this.utilsCommandLineRunner.booksReleasedBeforeDate();

//        6.	Authors Search
//        this.utilsCommandLineRunner.authorsSearch();

//        7.	Books Search
//        this.utilsCommandLineRunner.booksSearch();

//        8.	Book Titles Search
//        this.utilsCommandLineRunner.bookTitlesSearch();

//        9.	Count Books
//        this.utilsCommandLineRunner.countBooks();

//        10.	Total Book Copies
//        this.utilsCommandLineRunner.totalBookCopies();

//        11.	Reduced Book
//        this.utilsCommandLineRunner.reducedBook();

//        12.	* Increase Book Copies
//        this.utilsCommandLineRunner.increaseBookCopies();

//        13.	* Remove Books
//        this.utilsCommandLineRunner.removeBooks();

//        14.	* Stored Procedure
//        this.utilsCommandLineRunner.storedProcedure();
    }
}
