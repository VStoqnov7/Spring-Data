package softuni.exam.instagraphlite.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDetailDTO {
    private String caption;
    private double pictureSize;

    public PostDetailDTO(String caption, double pictureSize) {
        this.caption = caption;
        this.pictureSize = pictureSize;
    }
}
