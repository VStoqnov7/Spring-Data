package com.example.modelmapper_json_xml.entiti.sale;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleExportContainerDTO {

    @XmlElement(name = "sale")
    private List<SaleExportXmlDTO> sales;

    public List<SaleExportXmlDTO> getSales() {
        return sales;
    }

    public void setSales(List<SaleExportXmlDTO> sales) {
        this.sales = sales;
    }
}
