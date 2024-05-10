package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CarExportDTO {

    private String make;
    private String model;
    private Integer kilometers;
    private LocalDate registeredOn;
    private Integer countPicture;

    public CarExportDTO(String make, String model, Integer kilometers, LocalDate registeredOn, Integer countPicture) {
        this.make = make;
        this.model = model;
        this.kilometers = kilometers;
        this.registeredOn = registeredOn;
        this.countPicture = countPicture;
    }
}
