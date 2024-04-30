package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ConstellationImportDTO {

    @Size(min = 3, max = 20)
    @NotNull
    private String name;

    @Size(min = 5)
    @NotNull
    private String description;
}
