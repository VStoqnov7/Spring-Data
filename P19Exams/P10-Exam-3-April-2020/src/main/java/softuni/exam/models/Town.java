package softuni.exam.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "towns")
public class Town extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer population;

    @Column(nullable = false)
    private String guide;

}
