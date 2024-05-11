package softuni.exam.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity{

    @Column(nullable = false)
    private String url;

    @OneToMany(mappedBy = "picture")
    private List<Team> teams;

    @OneToMany(mappedBy = "picture")
    private List<Player> players;
}
