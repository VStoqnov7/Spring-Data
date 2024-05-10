package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class CarImportDTO {

    @NotNull
    @Size(min = 3, max = 19)
    private String make;

    @NotNull
    @Size(min = 3, max = 19)
    private String model;

    @NotNull
    @Positive
    private Integer kilometers;

    @NotNull
    private LocalDate registeredOn;

}
