package com.example.models.dto;

import com.example.util.LocalDateAdapter;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportProjectDTO implements Serializable {
    @XmlElement
    private String name;

    @XmlElement
    @Size(min = 14)
    private String description;

    @XmlElement(name = "start-date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate startDate;

    @XmlElement(name = "is-finished")
    private boolean isFinished;

    @XmlElement
    @Positive
    private BigDecimal payment;

    @XmlElement
    private ImportCompanyDTO company;

    public ImportProjectDTO() {}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public ImportCompanyDTO getCompany() {
        return company;
    }
}