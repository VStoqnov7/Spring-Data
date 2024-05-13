package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {
    private final String COUNTRY_PATH = "src/main/resources/files/json/countries.json";
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validation;
    private final CountryRepository countryRepository;

    public CountryServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validation, CountryRepository countryRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validation = validation;
        this.countryRepository = countryRepository;
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

        CountryDTO[] countryDTO = gson.fromJson(readCountriesFromFile(), CountryDTO[].class);

        Arrays.stream(countryDTO)
                .forEach(dto-> {
                    boolean isValid = validation.isValid(dto);
                    Optional<Country> countryByName = this.countryRepository.findByName(dto.getName());
                    if (isValid && !countryByName.isPresent()){
                        Country country = modelMapper.map(dto,Country.class);
                        this.countryRepository.save(country);
                        sb.append(String.format("Successfully imported country %s - %s",dto.getName(),dto.getCapital())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid country").append(System.lineSeparator());
                    }
                });


        return sb.toString().trim();
    }
}
