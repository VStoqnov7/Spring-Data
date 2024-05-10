package softuni.exam.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime dateAndTime;

    @ManyToOne
    private Car car;

    @ManyToMany(mappedBy = "pictures")
    private List<Offer> offer;
}
