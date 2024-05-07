package softuni.exam.instagraphlite.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class PostImportDTO {

    @NotNull
    @Size(min = 21)
    private String caption;

    private PostImportUserDTO user;

    private PostPictureDTO picture;
}
