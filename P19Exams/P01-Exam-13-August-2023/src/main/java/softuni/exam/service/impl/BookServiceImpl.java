package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.BookImportDTO;
import softuni.exam.models.entity.Book;
import softuni.exam.repository.BookRepository;
import softuni.exam.service.BookService;
import softuni.exam.util.MyValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class BookServiceImpl implements BookService {
    private final String BOOKS_PATH = "src/main/resources/files/json/books.json";
    private final ModelMapper modelMapper;

    private BookRepository bookRepository;

    private MyValidator validation;

    private Gson gson;

    @Autowired
    public BookServiceImpl(ModelMapper modelMapper, BookRepository bookRepository, MyValidator validation, Gson gson) {
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
        this.validation = validation;
        this.gson = gson;
    }


    @Override
    public boolean areImported() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public String readBooksFromFile() throws IOException {
        return Files
                .readString(Path.of(BOOKS_PATH));
    }

    @Override
    public String importBooks() throws IOException {
        StringBuilder sb = new StringBuilder();

        BookImportDTO[] bookSeedDTO = gson.fromJson(readBooksFromFile(), BookImportDTO[].class);

        Arrays.stream(bookSeedDTO)
                .filter(bookSeedDto -> {
                    boolean isValid = validation.isValid(bookSeedDto);

                    Optional<Book> bookByTitle = bookRepository.findBookByTitle(bookSeedDto.getTitle());
                    if (bookByTitle.isPresent()) {
                        isValid = false;
                    }

                    sb.append(isValid
                                    ? String.format("Successfully imported book %s - %s", bookSeedDto.getAuthor()
                                    , bookSeedDto.getTitle())
                                    : "Invalid book")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(bookSeedDto -> modelMapper.map(bookSeedDto, Book.class))
                .forEach(bookRepository::save);




//        StringBuilder sb = new StringBuilder();
//
//        BookImportDTO[] bookSeedDTO = gson.fromJson(readBooksFromFile(), BookImportDTO[].class);
//
//        Arrays.stream(bookSeedDTO)
//                .forEach(dto -> {
//                    List<String> violations = validation.validate(dto);
//
//                    if (violations.isEmpty()) {
//                        Book book = modelMapper.map(dto, Book.class);
//                        bookRepository.save(book);
//
//                        sb.append(String.format("Successfully imported book %s - %s", book.getAuthor()
//                                , book.getTitle())).append(System.lineSeparator());
//                    } else {
//                        violations.forEach(message -> sb.append(message).append(System.lineSeparator()));
//                    }
//                });
//
//        return sb.toString().trim();
//

//
//        Arrays.stream(bookSeedDTO)
//                .forEach(dto -> {
//                    boolean isValid = validation.isValid(dto);
//
//                    if (isValid) {
//                        Book book = modelMapper.map(dto, Book.class);
//                        bookRepository.save(book);
//
//                        sb.append(String.format("Successfully imported book %s - %s", book.getAuthor()
//                                , book.getTitle())).append(System.lineSeparator());
//                    } else {
//                        sb.append("Invalid book").append(System.lineSeparator());
//                    }
//                });

        return sb.toString().trim();
    }
}
