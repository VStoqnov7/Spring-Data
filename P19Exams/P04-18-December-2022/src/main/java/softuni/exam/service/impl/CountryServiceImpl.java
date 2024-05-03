package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryImportDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.MyValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {
    private final String COUNTRY_PATH = "src/main/resources/files/json/countries.json";
    private final ModelMapper modelMapper;

    private final MyValidator validator;
    private final CountryRepository countryRepository;

    private final Gson gson;

    public CountryServiceImpl(ModelMapper modelMapper, CountryRepository countryRepository, MyValidator validator, Gson gson) {
        this.modelMapper = modelMapper;
        this.countryRepository = countryRepository;
        this.validator = validator;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFileContent() throws IOException {
        return Files.readString(Path.of(COUNTRY_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();

        CountryImportDTO[] countryDTO = gson.fromJson(readCountriesFileContent(),CountryImportDTO[].class);

        Arrays.stream(countryDTO)
                .filter(dto-> {
                    boolean isValid = validator.isValid(dto);

                    Optional<Country> byName = this.countryRepository.findByName(dto.getName());
                    Optional<Country> byCode = this.countryRepository.findByCode(dto.getCountryCode());

                    if (byName.isPresent() || byCode.isPresent()){
                        isValid = false;
                    }

                    if (isValid){
                        sb.append(String.format("Successfully imported country %s - %s",
                                dto.getName(),dto.getCountryCode())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid country").append(System.lineSeparator());
                    }
                    return isValid;
                })
                .map(dto -> {
                    Country country = modelMapper.map(dto,Country.class);
                    return country;
                })
                .forEach(dto-> this.countryRepository.save(dto));

        return sb.toString().trim();

    }
}
