package softuni.exam.domain.entities.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.domain.entities.Position;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
public class PlayerImportDTO {

    @NotNull
    private String firstName;

    @NotNull
    @Size(min = 3,max = 15)
    private String lastName;

    @NotNull
    @Min(1)
    @Max(99)
    private Integer number;

    @NotNull
    @Min(0)
    private BigDecimal salary;

    @NotNull
    private Position position;

    private PictureImportDTO picture;

    private TeamImportDTO team;

}
