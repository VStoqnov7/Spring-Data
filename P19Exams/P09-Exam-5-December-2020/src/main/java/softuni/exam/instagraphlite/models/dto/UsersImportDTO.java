package softuni.exam.instagraphlite.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.instagraphlite.models.Pictures;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UsersImportDTO {

    @NotNull
    @Size(min = 2, max = 18)
    private String username;

    @NotNull
    @Size(min = 4)
    private String password;

    @NotNull
    private String profilePicture;
}
