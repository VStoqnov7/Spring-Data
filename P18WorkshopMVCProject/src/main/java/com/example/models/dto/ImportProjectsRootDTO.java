package com.example.models.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportProjectsRootDTO {

    @XmlElement(name = "project")
    List<ImportProjectDTO> projects;

    public ImportProjectsRootDTO() {}

    public List<ImportProjectDTO> getProjects() {
        return projects;
    }
}