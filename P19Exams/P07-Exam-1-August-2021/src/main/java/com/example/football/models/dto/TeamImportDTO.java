package com.example.football.models.dto;

import com.example.football.models.entity.Town;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TeamImportDTO {

    @NotNull
    @Size(min = 3)
    private String name;

    @NotNull
    @Size(min = 3)
    private String stadiumName;

    @NotNull
    @Min(1000)
    private Integer fanBase;

    @NotNull
    @Size(min = 10)
    private String history;

    @NotNull
    private String townName;
}
