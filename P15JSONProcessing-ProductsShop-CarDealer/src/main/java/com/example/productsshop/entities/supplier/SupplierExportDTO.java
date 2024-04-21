package com.example.productsshop.entities.supplier;


import com.google.gson.annotations.SerializedName;

public class SupplierExportDTO {
    @SerializedName("Id")
    private Long id;

    @SerializedName("Name")
    private String name;

    private int partsCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(int partsCount) {
        this.partsCount = partsCount;
    }
}
