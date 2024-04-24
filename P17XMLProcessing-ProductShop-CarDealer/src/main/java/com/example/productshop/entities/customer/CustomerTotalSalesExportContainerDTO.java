package com.example.productshop.entities.customer;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerTotalSalesExportContainerDTO {

    @XmlElement
    private List<CustomerTotalSalesExportDTO> customers;

    public List<CustomerTotalSalesExportDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerTotalSalesExportDTO> customers) {
        this.customers = customers;
    }
}
