package softuni.exam.instagraphlite.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pictures")
public class Pictures extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String path;

    @Column(nullable = false)
    private Double size;

    @OneToMany(mappedBy = "picture")
    private List<Posts> posts;
}
