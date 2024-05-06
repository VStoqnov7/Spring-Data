package com.example.football.models.dto;

import com.example.football.models.entity.enums.Position;
import com.example.football.util.LocalDateAdapter;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerImportDTO {

    @NotNull
    @Size(min = 2)
    @XmlElement(name = "first-name")
    private String firstName;
    @NotNull
    @Size(min = 2)
    @XmlElement(name = "last-name")
    private String lastName;

    @NotNull
    @Pattern(regexp = ".*@.*\\..*")
    private String email;

    @NotNull
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = "birth-date")
    private LocalDate birthDate;

    @NotNull
    private Position position;

    private TownNameDTO town;

    private TeamNameDTO team;

    private StatIdDTO stat;
}
