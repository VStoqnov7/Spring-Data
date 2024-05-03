package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class JobImportDTO {

    @NotNull
    @Size(min = 2,max = 40)
    private String jobTitle;

    @NotNull
    @DecimalMin("10.00")
    private Double hoursAWeek;

    @NotNull
    @DecimalMin("300.00")
    private Double salary;

    @NotNull
    @Size(min = 5)
    private String description;

    @NotNull
    private Long companyId;
}
