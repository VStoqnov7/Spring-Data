package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CompanyImportContainerDTO;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CompanyService;
import softuni.exam.util.MyValidator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final String COMPANY_PATH = "src/main/resources/files/xml/companies.xml";
    private final ModelMapper modelMapper;

    private final MyValidator validator;
    private final CompanyRepository companyRepository;

    private final CountryRepository countryRepository;

    public CompanyServiceImpl(ModelMapper modelMapper, MyValidator validator, CompanyRepository companyRepository, CountryRepository countryRepository) {
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.companyRepository = companyRepository;
        this.countryRepository = countryRepository;
    }


    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesFromFile() throws IOException {
        return Files.readString(Path.of(COMPANY_PATH));
    }

    @Override
    public String importCompanies() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(CompanyImportContainerDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        CompanyImportContainerDTO companyDTO = (CompanyImportContainerDTO) unmarshaller.unmarshal(new File(COMPANY_PATH));

        companyDTO.getCompanies().stream()
                .filter(dto-> {
                    boolean isValid = validator.isValid(dto);

                    Optional<Company> byName = this.companyRepository.findByName(dto.getCompanyName());
                    if (byName.isPresent()){
                        isValid = false;
                    }

                    if (isValid){
                        sb.append(String.format("Successfully imported company %s - %d",
                                dto.getCompanyName(),dto.getCountryId())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid company").append(System.lineSeparator());
                    }
                    return isValid;
                })
                .map(dto-> {
                    Company company = modelMapper.map(dto,Company.class);
                    Country country = this.countryRepository.findById(dto.getCountryId()).orElse(null);
                    company.setCountry(country);
                    return company;
                }).forEach(this.companyRepository::save);

        return sb.toString().trim();
    }
}
