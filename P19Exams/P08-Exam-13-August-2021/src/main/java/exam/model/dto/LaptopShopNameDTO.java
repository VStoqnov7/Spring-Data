package exam.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LaptopShopNameDTO {

    @NotNull
    @Size(min = 2)
    private String name;

}
