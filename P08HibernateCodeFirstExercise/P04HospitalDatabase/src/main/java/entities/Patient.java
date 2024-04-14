package entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;


    private String address;

    private String email;

    private LocalDate dateOfBirth;

    private String picture;

    @Column(nullable = true)
    private boolean hasMedicalInsurance;

    @OneToMany(mappedBy = "patient")
    private List<Visitation> visitations;

    public Patient() {

    }

    @Override
    public String toString() {
        return String.format("%s %s",this.firstName,this.lastName);
    }
}
