package hiberspring.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeImportDTO {

    @NotNull
    @XmlAttribute(name = "first-name")
    private String firstName;

    @NotNull
    @XmlAttribute(name = "last-name")
    private String lastName;

    @NotNull
    @XmlAttribute
    private String position;

    @NotNull
    @XmlElement
    private String card;

    @NotNull
    @XmlElement
    private String branch;

}
