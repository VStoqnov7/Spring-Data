package softuni.exam.models;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.enums.Rating;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sellers")
public class Seller extends BaseEntity{

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(unique = true, nullable = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Column(nullable = false)
    private String town;

    @OneToMany(mappedBy = "seller")
    private List<Offer> offers;

}
