package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CountryImportDTO {

    @NotNull
    @Size(min = 2, max = 60)
    private String countryName;

    @NotNull
    @Size(min = 2, max = 20)
    private String currency;


}
