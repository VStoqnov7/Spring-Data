package softuni.exam.models.dto;

import softuni.exam.models.entity.enums.Genre;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class BookImportDTO {

    @Size(min = 3, max = 40)
    @NotNull
    private String title;
    @Size(min = 3, max = 40)
    @NotNull
    private String author;

    @Size(min = 5)
    @NotNull
    private String description;
    @NotNull
    private boolean available;
    @NotNull
    private Genre genre;
    @NotNull
    @Positive
    private Double rating;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
