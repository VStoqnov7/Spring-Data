package exam.model.dto;

import exam.model.entity.Town;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopImportDTO {

    @NotNull
    @Size(min = 4)
    private String address;

    @NotNull
    @Min(1)
    @Max(50)
    @XmlElement(name = "employee-count")
    private Integer employeeCount;

    @NotNull
    @DecimalMin("20000")
    private BigDecimal income;

    @NotNull
    @Size(min = 4)
    private String name;

    @NotNull
    @Min(150)
    @XmlElement(name = "shop-area")
    private Integer shopArea;

    private TownImportNameDTO town;

}
