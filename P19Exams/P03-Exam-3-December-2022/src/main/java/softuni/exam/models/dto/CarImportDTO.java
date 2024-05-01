package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.entity.enums.CarType;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class CarImportDTO {

    @NotNull
    @Size(min = 2, max = 30)
    private String carMake;

    @NotNull
    @Size(min = 2, max = 30)
    private String carModel;

    @NotNull
    @Positive
    private int year;

    @NotNull
    @Size(min = 2, max = 30)
    private String plateNumber;

    @NotNull
    @Positive
    private int kilometers;

    @NotNull
    @DecimalMin(value = "1.00")
    private Double engine;

    @NotNull
    private CarType carType;
}
