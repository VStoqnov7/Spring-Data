package softuni.exam.instagraphlite.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Posts extends BaseEntity{

    @Column(nullable = false)
    private String caption;

    @ManyToOne
    private Users user;

    @ManyToOne
    private Pictures picture;
}
