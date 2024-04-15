package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "squard_number")
    private short squardNumber;

    @ManyToOne
    private Team team;

    @ManyToOne
    private Position position;

    @Column(name = "is_currently_injured")
    private Boolean isCurrentlyInjured;
}
