package softuni.exam.domain.entities.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.domain.entities.Position;

@Getter
@Setter
public class PlayerExportDTO {
    private String firstName;
    private String lastName;
    private Position position;
    private Integer number;

    public PlayerExportDTO(String firstName, String lastName, Position position, Integer number) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.number = number;
    }
}
