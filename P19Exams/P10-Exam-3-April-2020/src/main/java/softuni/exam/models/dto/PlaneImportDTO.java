package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneImportDTO {

    @NotNull
    @Size(min = 5)
    @XmlElement(name = "register-number")
    private String registerNumber;

    @NotNull
    @Positive
    private Integer capacity;

    @NotNull
    @Size(min = 2)
    private String airline;

}
