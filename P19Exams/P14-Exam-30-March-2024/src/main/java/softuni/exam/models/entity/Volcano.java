package softuni.exam.models.entity;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.enums.VolcanoType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "volcanoes")
public class Volcano extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer elevation;


    @Column(name = "volcano_type")
    @Enumerated(EnumType.STRING)
    private VolcanoType volcanoType;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "last_eruption")
    private LocalDate lastEruption;


    @OneToMany(mappedBy = "volcano")
    private List<Volcanologist> volcanologists;

    @ManyToOne
    private Country country;
}
