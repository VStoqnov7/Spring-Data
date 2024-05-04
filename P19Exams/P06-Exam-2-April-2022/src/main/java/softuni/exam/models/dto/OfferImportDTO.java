package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.util.LocalDateAdapter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportDTO {

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate publishedOn;

    private AgentNameDTO agent;

    private ApartmentIdDTO apartment;
}
