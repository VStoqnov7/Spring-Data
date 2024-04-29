package softuni.exam.models.dto;

import java.time.LocalDate;

public class BorrowingRecordsExportDTO {

    private String bookTitle;

    private String bookAuthor;

    private LocalDate borrowDate;

    private String libraryMemberFirstName;

    private String libraryMemberLastName;

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getLibraryMemberFirstName() {
        return libraryMemberFirstName;
    }

    public void setLibraryMemberFirstName(String libraryMemberFirstName) {
        this.libraryMemberFirstName = libraryMemberFirstName;
    }

    public String getLibraryMemberLastName() {
        return libraryMemberLastName;
    }

    public void setLibraryMemberLastName(String libraryMemberLastName) {
        this.libraryMemberLastName = libraryMemberLastName;
    }
}
