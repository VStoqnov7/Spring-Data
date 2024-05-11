package softuni.exam.domain.entities.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class TeamNameAndPlayerExportDTO {
    private String teamName;

    List<PlayerExportDTO> players;

    public TeamNameAndPlayerExportDTO(String teamName) {
        this.teamName = teamName;
    }
}
