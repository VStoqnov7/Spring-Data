package softuni.exam.instagraphlite.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PictureImportDTO {

    @NotNull
    private String path;

    @NotNull
    @DecimalMin("500")
    @DecimalMax("60000")
    private Double size;
}
