package com.example.p02usersystem.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String caption;

    private String path;

}
