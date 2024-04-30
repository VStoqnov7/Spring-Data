package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.entity.enums.StarType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
public class StarImportDTO {

    @Size(min = 2, max = 30)
    @NotNull
    private String name;

    @Positive
    @NotNull
    private Double lightYears;

    @Size(min = 6)
    @NotNull
    private String description;

    @NotNull
    private StarType starType;

    @NotNull
    private Long constellation;
}
