package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.util.LocalDateTimeAdapterXml;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportDTO {

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Size(min = 5)
    private String description;

    @NotNull
    @XmlJavaTypeAdapter(LocalDateTimeAdapterXml.class)
    @XmlElement(name = "added-on")
    private LocalDateTime addedOn;

    @NotNull
    @XmlElement(name = "has-gold-status")
    private boolean hasGoldStatus;

    private OfferCarIdDTO car;

    private OfferSellerIdDTO seller;

}
