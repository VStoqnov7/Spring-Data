package softuni.exam.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "countries")
public class Country extends BaseEntity{

    @Column(name = "country_name", nullable = false)
    private String countryName ;

    @Column(nullable = false)
    private String currency;

    @OneToMany(mappedBy = "country")
    private List<City> cities;
}
