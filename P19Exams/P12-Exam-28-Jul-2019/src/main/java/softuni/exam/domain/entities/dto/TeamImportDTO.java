package softuni.exam.domain.entities.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamImportDTO {

    @NotNull
    @Size(min = 3, max = 20)
    private String name;

    private PictureImportDTO picture;

}
