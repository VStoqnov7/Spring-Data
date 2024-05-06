package exam.model.dto;

import exam.model.entity.Shop;
import exam.model.entity.enums.WarrantyType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
public class LaptopImportDTO {

    @NotNull
    @Size(min = 9)
    private String macAddress;

    @NotNull
    @Positive
    private Double cpuSpeed;

    @NotNull
    @Min(8)
    @Max(128)
    private Integer ram;

    @NotNull
    @Min(128)
    @Max(1024)
    private Integer storage;

    @NotNull
    @Size(min = 10)
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    private WarrantyType warrantyType;


    private LaptopShopNameDTO shop;

}
