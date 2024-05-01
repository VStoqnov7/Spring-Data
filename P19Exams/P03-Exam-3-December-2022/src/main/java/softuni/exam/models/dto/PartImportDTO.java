package softuni.exam.models.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class PartImportDTO {
    @NotNull
    @Size(min = 2, max = 19)
    private String partName;

    @NotNull
    @DecimalMin(value = "10.00")
    @DecimalMax(value = "2000.00")
    private Double price;

    @NotNull
    @Positive
    private int quantity;
}
