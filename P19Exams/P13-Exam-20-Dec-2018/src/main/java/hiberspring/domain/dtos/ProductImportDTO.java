package hiberspring.domain.dtos;

import hiberspring.domain.entities.Branch;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductImportDTO {

    @NotNull
    @XmlAttribute
    private String name;

    @NotNull
    @XmlAttribute
    private Integer clients;

    @NotNull
    private String branch;
}
