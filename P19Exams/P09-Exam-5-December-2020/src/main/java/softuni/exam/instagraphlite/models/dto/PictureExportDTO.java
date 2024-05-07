package softuni.exam.instagraphlite.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PictureExportDTO {
    private String path;
    private Double size;

    public PictureExportDTO(String path, Double size) {
        this.path = path;
        this.size = size;
    }
}
