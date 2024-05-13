package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class VolcanoExportDTO {

    private String name;

    private String countryName;

    private int elevation;

    private LocalDate lastEruption;


    public VolcanoExportDTO(String name, String countryName, int elevation, LocalDate lastEruption) {
        this.name = name;
        this.countryName = countryName;
        this.elevation = elevation;
        this.lastEruption = lastEruption;
    }
}
