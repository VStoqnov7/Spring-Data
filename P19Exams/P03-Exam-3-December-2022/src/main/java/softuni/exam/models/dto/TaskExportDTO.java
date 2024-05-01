package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class TaskExportDTO {

    private String carMake;

    private String carModel;

    private String mechanicFirstName;

    private String mechanicLastName;

    private Long id;

    private String carEngine;

    private BigDecimal price;

    private int carKilometers;
}
