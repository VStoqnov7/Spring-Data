package com.example.productshop.entities.car;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarImportContainerDTO {

    @XmlElement(name = "car")
    private List<CarImportDTO> cars;

    public List<CarImportDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarImportDTO> cars) {
        this.cars = cars;
    }
}
