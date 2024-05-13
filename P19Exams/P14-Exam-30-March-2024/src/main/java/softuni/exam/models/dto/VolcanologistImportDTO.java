package softuni.exam.models.dto;


import lombok.Getter;
import lombok.Setter;
import softuni.exam.util.LocalDateAdapterXml;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistImportDTO {

    @NotNull
    @Size(min = 2, max = 30)
    @XmlElement(name = "first_name")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30)
    @XmlElement(name = "last_name")
    private String lastName;

    @NotNull
    @Positive
    private Double salary;

    @NotNull
    @Min(18)
    @Max(80)
    private Integer age;

    @XmlJavaTypeAdapter(LocalDateAdapterXml.class)
    @XmlElement(name = "exploring_from")
    private LocalDate exploringFrom;

    @XmlElement(name = "exploring_volcano_id")
    private Long volcanoId;

}
