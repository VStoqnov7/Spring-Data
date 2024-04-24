package com.example.productshop.entities.car;


import com.example.productshop.entities.part.PartExportDTO;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class CarAndPartsExportDTO {

    @XmlAttribute
    private String make;
    @XmlAttribute
    private String model;
    @XmlAttribute(name = "travelled-distance")
    private Long travelledDistance;

    @XmlElementWrapper(name = "parts")
    @XmlElement(name = "part")
    private List<PartExportDTO> parts;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public List<PartExportDTO> getParts() {
        return parts;
    }

    public void setParts(List<PartExportDTO> parts) {
        this.parts = parts;
    }
}
