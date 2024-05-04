package softuni.exam.models.entity;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.entity.enums.ApartmentType;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "apartments")
public class Apartment extends BaseEntity{

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApartmentType apartmentType;

    @Column(nullable = false)
    private Double area;

    @ManyToOne
    private Town town;
}
