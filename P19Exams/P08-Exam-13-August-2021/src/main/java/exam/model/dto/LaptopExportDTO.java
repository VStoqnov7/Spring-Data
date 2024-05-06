package exam.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LaptopExportDTO {

    private String macAddress;
    private Double cpuSpeed;
    private Integer ram;
    private Integer storage;
    private BigDecimal price;
    private String shopName;
    private String townName;

    public LaptopExportDTO(String macAddress, Double cpuSpeed, Integer ram, Integer storage, BigDecimal price, String shopName, String townName) {
        this.macAddress = macAddress;
        this.cpuSpeed = cpuSpeed;
        this.ram = ram;
        this.storage = storage;
        this.price = price;
        this.shopName = shopName;
        this.townName = townName;
    }
}
