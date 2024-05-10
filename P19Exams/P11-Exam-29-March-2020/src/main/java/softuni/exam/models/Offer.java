package softuni.exam.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "offers")
public class Offer extends BaseEntity{

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "has_gold_status", nullable = false)
    private boolean hasGoldStatus;

    @Column(name = "added_on", nullable = false)
    private LocalDateTime addedOn;

    @ManyToOne
    private Car car;

    @ManyToOne
    private Seller seller;

    @ManyToMany
    private List<Picture> pictures;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offer)) return false;
        Offer offer = (Offer) o;
        return Objects.equals(getDescription(), offer.getDescription()) && Objects.equals(getAddedOn(), offer.getAddedOn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getAddedOn());
    }
}
