package com.example.p02usersystem.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "background_color")
    private String backgroundColor;


    @Column(name = "is_public")
    private boolean isPublic;

    @OneToMany
    @JoinColumn(name = "album_id")
    private List<Picture> pictures;
}
