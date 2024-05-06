package exam.model.entity;

import exam.model.entity.enums.WarrantyType;
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
@Table(name = "laptops")
public class Laptop extends BaseEntity{

    @Column(name = "mac_address", unique = true, nullable = false)
    private String macAddress;

    @Column(name = "cpu_speed", nullable = false)
    private Double cpuSpeed;

    @Column(nullable = false)
    private Integer ram;

    @Column(nullable = false)
    private Integer storage;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "warranty_type", nullable = false)
    private WarrantyType warrantyType;

    @ManyToOne
    private Shop shop;
}
