package softuni.exam.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "agents")
public class Agent extends BaseEntity{

    @Column(name = "first_name", unique = true, nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @ManyToOne
    private Town town;
}
