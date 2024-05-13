package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.enums.VolcanoType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class VolcanoDTO {

    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @NotNull
    @Positive
    private Integer elevation;

    private VolcanoType volcanoType;

    @NotNull
    private Boolean isActive;

    private LocalDate lastEruption;

    @NotNull
    private Long country;

}
