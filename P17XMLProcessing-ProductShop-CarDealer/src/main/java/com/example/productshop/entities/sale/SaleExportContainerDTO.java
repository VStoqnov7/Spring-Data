package com.example.productshop.entities.sale;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleExportContainerDTO {

    @XmlElement(name = "sale")
    private List<SaleExportDTO> sales;

    public List<SaleExportDTO> getSales() {
        return sales;
    }

    public void setSales(List<SaleExportDTO> sales) {
        this.sales = sales;
    }
}
