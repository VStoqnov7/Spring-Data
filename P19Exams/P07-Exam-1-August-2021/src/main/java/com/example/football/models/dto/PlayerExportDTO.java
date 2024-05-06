package com.example.football.models.dto;

import com.example.football.models.entity.enums.Position;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerExportDTO {

    private String firstName;
    private String lastName;
    private Position position;
    private String teamName;
    private String teamStadiumName;

    public PlayerExportDTO(String firstName, String lastName, Position position, String teamName, String teamStadiumName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.teamName = teamName;
        this.teamStadiumName = teamStadiumName;
    }
}
