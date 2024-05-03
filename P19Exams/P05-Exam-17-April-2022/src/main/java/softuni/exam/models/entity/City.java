package softuni.exam.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cities")
public class City extends BaseEntity{

    @Column(name = "city_name", unique = true, nullable = false)
    private String cityName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer population;

    @ManyToOne
    private Country country;

    @OneToMany(mappedBy = "city")
    private List<Forecast> forecasts;
}
