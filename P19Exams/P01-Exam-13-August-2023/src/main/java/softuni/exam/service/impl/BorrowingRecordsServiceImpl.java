package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.BorrowingRecordsContainerXMLDTO;
import softuni.exam.models.dto.BorrowingRecordsExportDTO;
import softuni.exam.models.dto.BorrowingRecordsXMLDTO;
import softuni.exam.models.dto.ExampleMappingDTO;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.BookRepository;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.BorrowingRecordsService;
import softuni.exam.util.MyValidator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {

    private final String BORROWING_RECORDS_PATH = "src/main/resources/files/xml/borrowing-records.xml";
    private BorrowingRecordRepository borrowingRecordRepository;

    private MyValidator validation;
    private LibraryMemberRepository libraryMemberRepository;

    private BookRepository bookRepository;
    private ModelMapper modelMapper;

    @Autowired
    public BorrowingRecordsServiceImpl(BorrowingRecordRepository borrowingRecordRepository,
                                       MyValidator validator,
                                       LibraryMemberRepository libraryMemberRepository,
                                       BookRepository bookRepository,
                                       ModelMapper modelMapper) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.validation = validator;
        this.libraryMemberRepository = libraryMemberRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
//        configureModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.borrowingRecordRepository.count() > 0;
    }

    @Override
    public String readBorrowingRecordsFromFile() throws IOException {
        return Files
                .readString(Path.of(BORROWING_RECORDS_PATH));
    }

    @Override
    public String importBorrowingRecords() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(BorrowingRecordsContainerXMLDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        BorrowingRecordsContainerXMLDTO borrowingRecordsContainerXMLDTO = (BorrowingRecordsContainerXMLDTO) unmarshaller.unmarshal(new File(BORROWING_RECORDS_PATH));

        StringBuilder sb = new StringBuilder();

        for (BorrowingRecordsXMLDTO borrowingRecordXMLDTO : borrowingRecordsContainerXMLDTO.getBorrowingRecords()) {
            boolean isValid = validation.isValid(borrowingRecordXMLDTO);

            Optional<Book> bookByTitle = bookRepository.findBookByTitle(borrowingRecordXMLDTO.getBook().getTitle());
            if (bookByTitle.isEmpty()) {
                isValid = false;
            }

            Optional<LibraryMember> libraryMember = libraryMemberRepository.findById(borrowingRecordXMLDTO.getLibraryMember().getId());
            if (libraryMember.isEmpty()) {
                isValid = false;
            }

            sb.append(isValid
                            ? String.format("Successfully imported borrowing record %s - %s",
                            borrowingRecordXMLDTO.getBook().getTitle(),
                            borrowingRecordXMLDTO.getBorrowDate())
                            : "Invalid borrowing record")
                    .append(System.lineSeparator());

            if (isValid) {
                BorrowingRecord borrowingRecord = modelMapper.map(borrowingRecordXMLDTO, BorrowingRecord.class);
                borrowingRecord.setBook(bookByTitle.get());
                borrowingRecord.setLibraryMember(libraryMember.get());
                borrowingRecordRepository.save(borrowingRecord);
            }
        }

        return sb.toString().trim();
    }

    @Override
    public String exportBorrowingRecords() {
        StringBuilder sb = new StringBuilder();

        List<BorrowingRecord> borrowingRecords = borrowingRecordRepository.findBorrowingRecordsInfo();

        List<BorrowingRecordsExportDTO> borrowingRecordsDTO = borrowingRecords.stream()
                .map(borrowingRecord -> modelMapper.map(borrowingRecord, BorrowingRecordsExportDTO.class))
                .collect(Collectors.toList());


        borrowingRecordsDTO.forEach(borrowingRecord -> {
            sb.append(String.format("Book title: %s", borrowingRecord.getBookTitle())).append(System.lineSeparator());
            sb.append(String.format("*Book author: %s",borrowingRecord.getBookAuthor())).append(System.lineSeparator());
            sb.append(String.format("**Date borrowed: %s",borrowingRecord.getBorrowDate())).append(System.lineSeparator());
            sb.append(String.format("***Borrowed by: %s %s",borrowingRecord.getLibraryMemberFirstName(),borrowingRecord.getLibraryMemberLastName())).append(System.lineSeparator());
        });


//        configureModelMapper   ->
//        List<BorrowingRecord> borrowingRecords = borrowingRecordRepository.findBorrowingRecordsInfo();
//
//        List<ExampleMappingDTO> borrowingRecordsExampleDTO = borrowingRecords.stream()
//                .map(borrowingRecord -> modelMapper.map(borrowingRecord, ExampleMappingDTO.class))
//                .collect(Collectors.toList());
//
//
//        borrowingRecordsExampleDTO.forEach(borrowingRecord -> {
//            sb.append(String.format("Book title: %s", borrowingRecord.getTitle())).append(System.lineSeparator());
//            sb.append(String.format("*Book author: %s", borrowingRecord.getAuthor())).append(System.lineSeparator());
//            sb.append(String.format("**Date borrowed: %s", borrowingRecord.getBorrowDate())).append(System.lineSeparator());
//            sb.append(String.format("***Borrowed by: %s %s", borrowingRecord.getFirstName(), borrowingRecord.getLastName())).append(System.lineSeparator());
//        });



        return sb.toString().trim();
    }

//    private void configureModelMapper() {
//        modelMapper.typeMap(BorrowingRecord.class, ExampleMappingDTO.class)
//                .addMapping(src -> src.getBook().getTitle(), ExampleMappingDTO::setTitle)
//                .addMapping(src -> src.getBook().getAuthor(), ExampleMappingDTO::setAuthor)
//                .addMapping(src -> src.getLibraryMember().getFirstName(), ExampleMappingDTO::setFirstName)
//                .addMapping(src -> src.getLibraryMember().getLastName(), ExampleMappingDTO::setLastName);
//    }
}
