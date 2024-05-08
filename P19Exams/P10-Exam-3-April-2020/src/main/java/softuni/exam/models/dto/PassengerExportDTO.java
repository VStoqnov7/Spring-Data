package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerExportDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private int countTickets;

    public PassengerExportDTO(String firstName, String lastName, String email, String phoneNumber, int countTickets) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.countTickets = countTickets;
    }
}
