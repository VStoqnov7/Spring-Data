package softuni.exam.models.entity;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.entity.enums.StarType;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "stars")
public class Star {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "light_years",nullable = false)
    private Double lightYears;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "star_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private StarType starType;

    @OneToMany(mappedBy = "observingStar")
    private List<Astronomer> observers;

    @ManyToOne
    private Constellation constellation;

}
