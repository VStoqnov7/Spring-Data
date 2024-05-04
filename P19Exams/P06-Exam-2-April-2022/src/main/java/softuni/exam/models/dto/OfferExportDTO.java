package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OfferExportDTO {

    private String firstName;

    private String lastName;

    private Long id;

    private double area;

    private String townName;

    private BigDecimal price;

    public OfferExportDTO(String firstName, String lastName, Long id, double area, String townName, BigDecimal price) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.area = area;
        this.townName = townName;
        this.price = price;
    }
}
