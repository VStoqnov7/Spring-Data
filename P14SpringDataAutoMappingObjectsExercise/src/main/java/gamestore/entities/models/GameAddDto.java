package gamestore.entities.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameAddDto {
    private String title;
    private BigDecimal price;
    private BigDecimal size;
    private String trailer;
    private String thumbnailURL;
    private String description;
    private LocalDate releaseDate;

    public GameAddDto(String title, BigDecimal price, BigDecimal size, String trailer, String thumbnailURL, String description, LocalDate releaseDate) {
        this.setTitle(title);
        this.setPrice(price);
        this.setSize(size);
        this.setTrailer(trailer);
        this.setThumbnailURL(thumbnailURL);
        this.setDescription(description);
        this.setReleaseDate(releaseDate);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (!Character.isUpperCase(title.charAt(0)) && title.length() < 3 && title.length() > 100) {
            throw new IllegalArgumentException("Invalid title");
        }
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price.signum() < 1) {
            throw new IllegalArgumentException("Invalid price");
        }
        this.price = price;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
//        String videoId = extractVideoId(trailer);
//        if (videoId == null) {
//            throw new IllegalArgumentException("Invalid YouTube trailer URL");
//        }
        this.trailer = trailer;
    }

    private String extractVideoId(String youtubeUrl) {
        String youtubeRegex = "^https?:\\/\\/(www\\.)?(youtube\\.com\\/watch\\?v=|youtu\\.be\\/)([a-zA-Z0-9_-]{11})$";
        Pattern pattern = Pattern.compile(youtubeRegex);
        Matcher matcher = pattern.matcher(youtubeUrl);

        if (matcher.matches()) {
            return matcher.group(3);
        } else {
            return null;
        }
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        if (!isValidImageUrl(thumbnailURL)) {
            throw new IllegalArgumentException("Invalid URL");
        }
        this.thumbnailURL = thumbnailURL;
    }

    private boolean isValidImageUrl(String url) {
        String imageUrlRegex = "^https?:\\/\\/.+$";
        Pattern pattern = Pattern.compile(imageUrlRegex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description.length() < 20){
            throw new IllegalArgumentException("Description must be at least 20 characters long");
        }
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
