package com.example.football.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class StatImportDTO {

    @NotNull
    @Positive
    private float shooting;

    @NotNull
    @Positive
    private float passing;

    @NotNull
    @Positive
    private float endurance;
}
