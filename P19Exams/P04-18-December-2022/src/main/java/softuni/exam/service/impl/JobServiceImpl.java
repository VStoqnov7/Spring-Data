package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.JobExportDTO;
import softuni.exam.models.dto.JobImportContainerDTO;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Job;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.JobRepository;
import softuni.exam.service.JobService;
import softuni.exam.util.MyValidator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    private final String JOB_PATH = "src/main/resources/files/xml/jobs.xml";
    private final ModelMapper modelMapper;

    private final MyValidator validator;

    private final JobRepository jobRepository;

    private final CompanyRepository companyRepository;

    public JobServiceImpl(ModelMapper modelMapper, MyValidator validator, JobRepository jobRepository, CompanyRepository companyRepository) {
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public boolean areImported() {
        return this.jobRepository.count() > 0;
    }

    @Override
    public String readJobsFileContent() throws IOException {
        return Files.readString(Path.of(JOB_PATH));
    }

    @Override
    public String importJobs() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(JobImportContainerDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        JobImportContainerDTO jobDTO = (JobImportContainerDTO) unmarshaller.unmarshal(new File(JOB_PATH));

        jobDTO.getJobs().stream()
                .filter(dto-> {
                    boolean isValid = validator.isValid(dto);

                    if (isValid){
                        sb.append(String.format("Successfully imported job %s",
                                dto.getJobTitle())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid job").append(System.lineSeparator());
                    }
                    return isValid;
                })
                .map(dto-> {
                    Job job = modelMapper.map(dto,Job.class);
                    Company company = this.companyRepository.findById(dto.getCompanyId()).orElse(null);
                    job.setCompany(company);
                    return job;
                }).forEach(this.jobRepository::save);

        return sb.toString().trim();
    }

    @Override
    public String getBestJobs() {
        StringBuilder sb = new StringBuilder();
        List<JobExportDTO> jobExportDTO  = this.jobRepository.findJobTitleSalaryAndHoursOrderBySalaryDesc();

        jobExportDTO.stream().forEach(dto-> {
            sb.append(String.format("Job title %s\n",dto.getTitle()));
            sb.append(String.format("-Salary: %.2f$\n",dto.getSalary()));
            sb.append(String.format("--Hours a week: %.2fh.\n\n", dto.getHoursAWeek()));
        });

        return sb.toString().trim();
    }
}
