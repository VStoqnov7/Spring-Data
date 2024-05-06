package com.example.football.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "towns")
public class Town extends BaseEntity{

    @Column(unique = false, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer population;

    @Column(name = "travel_guide", columnDefinition = "TEXT", nullable = false)
    private String travelGuide;

}
