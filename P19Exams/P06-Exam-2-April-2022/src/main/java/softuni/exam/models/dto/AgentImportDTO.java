package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.entity.Town;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AgentImportDTO {

    @NotNull
    @Size(min = 2)
    private String firstName;

    @NotNull
    @Size(min = 2)
    private String lastName;

    @NotNull
    @Pattern(regexp = ".*@.*\\..*")
    private String email;

    @NotNull
    private String town;
}
