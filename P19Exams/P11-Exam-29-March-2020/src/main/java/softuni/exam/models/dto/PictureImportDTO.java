package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.Car;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class PictureImportDTO {

    @NotNull
    @Size(min = 3, max = 19)
    private String name;

    @NotNull
    private LocalDateTime dateAndTime;

    @NotNull
    private Long car;

}
