package softuni.exam.domain.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "teams")
public class Team extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Picture picture;

    @OneToMany(mappedBy = "team")
    private List<Player> teams;

}
