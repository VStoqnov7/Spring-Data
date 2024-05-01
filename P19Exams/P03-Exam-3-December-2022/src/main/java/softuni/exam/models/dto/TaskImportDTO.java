package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.util.LocalDateTimeAdapter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskImportDTO {

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @NotNull
    private LocalDateTime date;

    @Positive
    @NotNull
    private BigDecimal price;

    @NotNull
    private CarIdImportDTO car;

    @NotNull
    private MechanicFirstNameDTO mechanic;

    @NotNull
    private PartIdDTO part;

}
