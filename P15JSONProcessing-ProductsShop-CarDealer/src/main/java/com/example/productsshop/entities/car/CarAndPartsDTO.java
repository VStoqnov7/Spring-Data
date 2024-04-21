package com.example.productsshop.entities.car;

import com.example.productsshop.entities.part.Part;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarAndPartsDTO {

    @SerializedName("Make")
    private String make;
    @SerializedName("Model")

    private String model;
    @SerializedName("TravelledDistance")
    private Long travelledDistance;

    private List<Part> parts;

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

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
