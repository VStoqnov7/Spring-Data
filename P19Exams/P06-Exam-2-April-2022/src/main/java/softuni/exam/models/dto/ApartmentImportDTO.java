package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.entity.enums.ApartmentType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentImportDTO {

    @NotNull
    private ApartmentType apartmentType;

    @NotNull
    @DecimalMin("40")
    private Double area;

    @NotNull
    private String town;
}
