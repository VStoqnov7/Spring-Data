package exam.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "shops")
public class Shop extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal income;

    @Column(nullable = false)
    private String address;

    @Column(name = "employee_count", nullable = false)
    private Integer employeeCount;

    @Column(name = "shop_area", nullable = false)
    private Integer shopArea;

    @ManyToOne
    private Town town;


}
