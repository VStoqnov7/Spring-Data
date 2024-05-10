package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.enums.Rating;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerImportDTO {

    @NotNull
    @Size(min = 3, max = 19)
    @XmlElement(name = "first-name")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 19)
    @XmlElement(name = "last-name")
    private String lastName;

    @NotNull
    @Pattern(regexp = ".*@.*\\..*")
    private String email;

    @NotNull
    private Rating rating;

    @NotNull
    private String town;

}
