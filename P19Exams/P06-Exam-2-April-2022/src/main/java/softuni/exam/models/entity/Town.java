package softuni.exam.models.entity;

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

    @Column(name = "town_name", unique = true, nullable = false)
    private String townName;

    @Column(nullable = false)
    private Integer population;
}
