package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.enums.DayOfWeek;

import java.time.LocalTime;
@Getter
@Setter
public class ForecastExportDTO {

    private String cityName;

    private double minTemperature;

    private double maxTemperature;

    private LocalTime sunrise;

    private LocalTime sunset;

    public ForecastExportDTO(String cityName, double minTemperature, double maxTemperature, LocalTime sunrise, LocalTime sunset) {
        this.cityName = cityName;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }
}
