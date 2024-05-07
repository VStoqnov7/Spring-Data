package softuni.exam.instagraphlite.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "profile_picture_id")
    private Pictures picture;

    @OneToMany(mappedBy = "user")
    private List<Posts> posts;
}
