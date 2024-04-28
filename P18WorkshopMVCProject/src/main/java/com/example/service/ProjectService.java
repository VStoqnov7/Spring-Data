package com.example.service;

import com.example.models.Company;
import com.example.models.Project;
import com.example.models.dto.ImportProjectDTO;
import com.example.models.dto.ImportProjectsRootDTO;
import com.example.repositories.CompanyRepository;
import com.example.repositories.ProjectRepository;
import com.example.util.MyValidator;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final Path xmlPath = Path.of("src/main/resources/files/xmls/projects.xml");

    private final MyValidator myValidator;
    private final CompanyRepository companyRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectService(MyValidator myValidator,
                          ModelMapper modelMapper,
                          CompanyRepository companyRepository,
                          ProjectRepository projectRepository) {
        this.myValidator = myValidator;
        this.modelMapper = modelMapper;

        this.companyRepository = companyRepository;
        this.projectRepository = projectRepository;
    }

    public boolean areImported() {
        return this.projectRepository.count() > 0;
    }

    public String getProjectsText() throws IOException {
        return Files.readString(xmlPath);
    }

    public String importProjects() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(ImportProjectsRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportProjectsRootDTO rootDTO = (ImportProjectsRootDTO)
                unmarshaller.unmarshal(new FileReader(xmlPath.toAbsolutePath().toString()));

        StringBuilder sb = new StringBuilder();

        List<ImportProjectDTO> projects = rootDTO.getProjects();
        for (ImportProjectDTO dto : projects) {
            if (!myValidator.isValid(dto)) {
                sb.append("Invalid Project\n");
                continue;
            }

            Project project =
                    this.modelMapper.map(dto, Project.class);

            Optional<Company> optionalCompany = companyRepository.findByName(dto.getCompany().getName());

            if (optionalCompany.isPresent()) {
                Company company = optionalCompany.get();
                project.setCompany(company);
            } else {
                Company newCompany = new Company(dto.getCompany().getName());
                project.setCompany(newCompany);
                companyRepository.save(newCompany);
            }

            projectRepository.saveAndFlush(project);

            sb.append("Created Project - ")
                    .append(project.getName())
                    .append(" for company ")
                    .append(project.getCompany().getName())
                    .append("\n");
        }

        return sb.toString();
    }

    public String getFinishedProjects() {
        List<Project> projects = this.projectRepository.findByIsFinishedTrueOrderByPaymentDesc();

        return projects
                .stream()
                .map(Project::toString)
                .collect(Collectors.joining("\n"));
    }

}