package softuni.exam.models.dto;

import softuni.exam.util.LocalDateAdapterXml;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordsXMLDTO {

    @XmlElement(name = "borrow_date")
    @XmlJavaTypeAdapter(LocalDateAdapterXml.class)
    @NotNull
    private LocalDate borrowDate;
    @XmlElement(name = "return_date")
    @XmlJavaTypeAdapter(LocalDateAdapterXml.class)
    @NotNull
    private LocalDate returnDate;
    @XmlElement
    @Size(min = 3,max = 100)
    private String remarks;
    @XmlElement
    @NotNull
    private BookXmlDTO book;

    @XmlElement(name = "member")
    @NotNull
    private LibraryMemberXMLDTO libraryMember;

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public BookXmlDTO getBook() {
        return book;
    }

    public void setBook(BookXmlDTO book) {
        this.book = book;
    }

    public LibraryMemberXMLDTO getLibraryMember() {
        return libraryMember;
    }

    public void setLibraryMember(LibraryMemberXMLDTO libraryMember) {
        this.libraryMember = libraryMember;
    }
}
