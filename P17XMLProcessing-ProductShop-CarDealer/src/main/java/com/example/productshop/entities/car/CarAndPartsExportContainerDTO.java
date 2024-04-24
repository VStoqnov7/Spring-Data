package com.example.productshop.entities.car;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarAndPartsExportContainerDTO {

    @XmlElement(name = "car")
    private List<CarAndPartsExportDTO> cars;

    public List<CarAndPartsExportDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarAndPartsExportDTO> cars) {
        this.cars = cars;
    }
}
