package softuni.exam.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Double salary;

    @Column(name = "hours_a_week", nullable = false)
    private Double hoursAWeek;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private Company company;

}
