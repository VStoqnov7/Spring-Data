package softuni.exam.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "cars")
public class Car extends BaseEntity{

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer kilometers;

    @Column(name = "registered_on", nullable = false)
    private LocalDate registeredOn;

    @OneToMany(mappedBy = "car")
    private List<Picture> pictures;

    @OneToMany(mappedBy = "car")
    private List<Offer> offers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return Objects.equals(getMake(), car.getMake()) && Objects.equals(getModel(), car.getModel()) && Objects.equals(getKilometers(), car.getKilometers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMake(), getModel(), getKilometers());
    }
}
