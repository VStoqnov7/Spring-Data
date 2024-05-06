package com.example.football.models.entity;

import com.example.football.models.entity.enums.Position;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends BaseEntity{

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;


    @Column(nullable = false)
    private Position position;

    @ManyToOne
    private Stat stat;

    @ManyToOne
    private Town town;

    @ManyToOne
    private Team team;

}
