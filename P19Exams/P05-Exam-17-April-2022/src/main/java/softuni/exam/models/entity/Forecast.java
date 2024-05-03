package softuni.exam.models.entity;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.enums.DayOfWeek;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "forecasts")
public class Forecast extends BaseEntity{

    @Column(name = "day_of_week", nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(name = "max_temperature", nullable = false)
    private Double maxTemperature;

    @Column(name = "min_temperature", nullable = false)
    private Double minTemperature;

    @Column(nullable = false)
    private LocalTime sunrise;

    @Column(nullable = false)
    private LocalTime sunset;

    @ManyToOne
    private City city;
}
