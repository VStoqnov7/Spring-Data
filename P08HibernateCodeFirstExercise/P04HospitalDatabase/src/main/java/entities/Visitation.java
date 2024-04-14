package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "visitation")
public class Visitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String comments;

    @ManyToOne()
    private Patient patient;

    @OneToOne(mappedBy = "visitation")
    private Diagnosis diagnosis;

    @OneToOne(mappedBy = "visitation")
    private Medicament medicament;

    public Visitation() {
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", date=" + date +
                ", comments='" + comments + '\'' +
                ", patient=" + patient +
                ", diagnosis=" + diagnosis +
                ", medicament=" + medicament;
    }
}
