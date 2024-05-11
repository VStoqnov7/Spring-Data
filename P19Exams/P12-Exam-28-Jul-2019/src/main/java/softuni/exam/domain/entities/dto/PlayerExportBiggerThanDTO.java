package softuni.exam.domain.entities.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PlayerExportBiggerThanDTO {

    private String firstName;
    private String lastName;
    private Integer number;
    private BigDecimal salary;
    private String teamName;

    public PlayerExportBiggerThanDTO(String firstName, String lastName, Integer number, BigDecimal salary, String teamName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.salary = salary;
        this.teamName = teamName;
    }
}
