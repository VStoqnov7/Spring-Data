package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.util.LocalDateAdapter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;


@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyImportDTO {

    @NotNull
    @Size(min = 2,max = 40)
    private String companyName;

    @NotNull
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateEstablished;

    @NotNull
    @Size(min = 2,max = 30)
    private String website;

    @NotNull
    private Long countryId;
}
