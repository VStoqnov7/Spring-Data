package exam.model.dto;

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
public class TownImportDTO {

    @NotNull
    @Size(min = 2)
    private String name;

    @NotNull
    @Positive
    private Integer population;

    @NotNull
    @Size(min = 10)
    @XmlElement(name = "travel-guide")
    private String travelGuide;
}
