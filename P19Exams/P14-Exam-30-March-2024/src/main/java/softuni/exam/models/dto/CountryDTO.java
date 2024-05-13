package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CountryDTO {

    @NotNull
    @Size(min = 3, max = 30)
    private String name;

    @Size(min = 3, max = 30)
    private String capital;
}
