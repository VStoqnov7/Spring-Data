package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @Column(length = 3)
    private String id;

    private String name;

    @ManyToMany
    @JoinTable(name = "countries_continents",
            joinColumns = @JoinColumn(name = "country_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "continent_id", referencedColumnName = "id")
    )
    private Set<Continent> continents;
}
