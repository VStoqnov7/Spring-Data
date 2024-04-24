package com.example.productshop.entities.customer;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerExportContainerDTO {

    @XmlElement(name = "customer")
    private List<CustomerExportDTO> customers;

    public List<CustomerExportDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerExportDTO> customers) {
        this.customers = customers;
    }
}
