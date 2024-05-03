package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryImportDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.MyValidator;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class CountryServiceImpl implements CountryService {

    private final String COUNTRY_PATH = "src/main/resources/files/json/countries.json";
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;

    private final Gson gson;

    private final MyValidator validator;
    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, Gson gson, MyValidator validator) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(COUNTRY_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();

        CountryImportDTO[] countryDTOs = gson.fromJson(readCountriesFromFile(), CountryImportDTO[].class);

        Arrays.stream(countryDTOs)
                .forEach(dto -> {
                    boolean isValid = validator.isValid(dto);

                    if (isValid) {
                        Country country = modelMapper.map(dto, Country.class);
                        countryRepository.save(country);

                        sb.append(String.format("Successfully imported country %s - %s",
                                dto.getCountryName(), dto.getCurrency())).append(System.lineSeparator());
                    } else {
                         sb.append("Invalid country").append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }
}
