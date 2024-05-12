package hiberspring.domain.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeExportDTO {

    private String firstName;
    private String lastName;
    private String position;
    private String employeeCard;

    public EmployeeExportDTO(String firstName, String lastName, String position, String employeeCard) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.employeeCard = employeeCard;
    }
}
