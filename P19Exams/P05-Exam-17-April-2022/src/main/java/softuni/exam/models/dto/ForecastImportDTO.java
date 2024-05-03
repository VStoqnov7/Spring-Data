package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.entity.City;
import softuni.exam.models.enums.DayOfWeek;
import softuni.exam.util.LocalTimeAdapter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalTime;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastImportDTO {

    @NotNull
    @XmlElement(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @NotNull
    @DecimalMin("-20")
    @DecimalMax("60")
    @XmlElement(name = "max_temperature")
    private Double maxTemperature;

    @NotNull
    @DecimalMin("-50")
    @DecimalMax("40")
    @XmlElement(name = "min_temperature")
    private Double minTemperature;

    @NotNull
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime sunrise;

    @NotNull
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime sunset;

    @NotNull
    private Long city;
}
