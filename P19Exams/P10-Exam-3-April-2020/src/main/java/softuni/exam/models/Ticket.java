package softuni.exam.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity{

    @Column(name = "serial_number", unique = true, nullable = false)
    private String serialNumber;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "take_off", nullable = false)
    private LocalDateTime takeOff;

    @ManyToOne
    @JoinColumn(name = "from_town_id")
    private Town fromTown;

    @ManyToOne
    @JoinColumn(name = "to_town_id")
    private Town toTown;

    @ManyToOne
    private Plane plane;

    @ManyToOne
    private Passenger passenger;
}
