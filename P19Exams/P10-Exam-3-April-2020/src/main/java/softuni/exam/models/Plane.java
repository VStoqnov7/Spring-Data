package softuni.exam.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "planes")
public class Plane extends BaseEntity{

    @Column(name = "register_number", unique = true, nullable = false)
    private String registerNumber;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private String airline;
}
