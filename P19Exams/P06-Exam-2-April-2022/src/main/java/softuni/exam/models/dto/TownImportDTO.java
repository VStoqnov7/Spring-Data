package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TownImportDTO {

    @NotNull
    @Size(min = 2)
    private String townName;

    @NotNull
    @Positive
    private Integer population;
}
