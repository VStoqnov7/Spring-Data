package hiberspring.domain.entities;

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
@Table(name = "employee_cards")
public class EmployeeCard extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String number;

    @OneToMany(mappedBy = "card")
    private List<Employee> employees;
}
