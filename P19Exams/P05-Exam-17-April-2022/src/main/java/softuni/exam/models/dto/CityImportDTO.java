package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CityImportDTO {

    @NotNull
    @Size(min = 2, max = 60)
    private String cityName;

    @NotNull
    @Size(min = 2)
    private String description;

    @NotNull
    @Min(500)
    private Integer population;

    @NotNull
    private Long country;
}
