package softuni.exam.models.entity;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.entity.enums.StatusType;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "people")

public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", unique = true, nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column(name = "status_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    @ManyToOne
    private Country country;


}
