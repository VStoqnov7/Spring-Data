package com.example.productshop.entities.car;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarExportContainerDTO {

    @XmlElement(name = "car")
    private List<CarExportDTO> cars;

    public List<CarExportDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarExportDTO> cars) {
        this.cars = cars;
    }
}
