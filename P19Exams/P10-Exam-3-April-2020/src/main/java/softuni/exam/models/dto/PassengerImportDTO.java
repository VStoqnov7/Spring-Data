package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PassengerImportDTO {

    @NotNull
    @Size(min = 2)
    private String firstName;

    @NotNull
    @Size(min = 2)
    private String lastName;

    @NotNull
    @Positive
    private Integer age;

    @NotNull
    private String phoneNumber;

    @NotNull
    @Pattern(regexp = ".*@.*\\..*")
    private String email;

    @NotNull
    private String town;
}
