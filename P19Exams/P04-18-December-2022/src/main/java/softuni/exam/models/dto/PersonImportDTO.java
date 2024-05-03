package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.enums.StatusType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PersonImportDTO {

    @NotNull
    @Size(min = 2,max = 30)
    private String firstName;

    @NotNull
    @Size(min = 2,max = 30)
    private String lastName;

    @NotNull
    @Pattern(regexp = ".*@.*\\..*")
    private String email;

    @Size(min = 2,max = 13)
    private String phone;

    @NotNull
    private StatusType statusType;

    private Long country;

}
