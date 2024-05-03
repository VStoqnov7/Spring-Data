package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobExportDTO {

    private String title;

    private double salary;

    private double hoursAWeek;

    public JobExportDTO(String title, double salary, double hoursAWeek) {
        this.title = title;
        this.salary = salary;
        this.hoursAWeek = hoursAWeek;
    }
}
