package softuni.exam.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.util.LocalDateTimeAdapter;

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
public class TicketImportDTO {

    @XmlElement(name = "serial-number")
    @NotNull
    @Size(min = 2)
    private String serialNumber;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull

    @XmlElement(name = "take-off")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime takeOff;

    @XmlElement(name = "from-town")
    private TicketTownName fromTown;

    @XmlElement(name = "to-town")
    private TicketTownName toTown;

    private TicketPassengerEmail passenger;

    private TicketPlaneRegisterNumber plane;
}
