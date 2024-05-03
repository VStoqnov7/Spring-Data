package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CityImportDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.MyValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private final String CITY_PATH = "src/main/resources/files/json/cities.json";

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    private final ModelMapper modelMapper;

    private final MyValidator validator;
    private final Gson gson;

    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, ModelMapper modelMapper, MyValidator validator, Gson gson) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.gson = gson;
    }


    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(CITY_PATH));
    }

    @Override
    public String importCities() throws IOException {
        StringBuilder sb = new StringBuilder();

        CityImportDTO[] cityDTO =  gson.fromJson(readCitiesFileContent(),CityImportDTO[].class);

        Arrays.stream(cityDTO)
                .forEach(dto-> {
                    boolean isValid = validator.isValid(dto);
                    boolean isPresent = this.cityRepository.findByCityName(dto.getCityName()).isPresent();
                    if (isValid && !isPresent){
                        City city = modelMapper.map(dto,City.class);
                        Optional<Country> country = this.countryRepository.findById(dto.getCountry());
                        city.setCountry(country.get());
                        this.cityRepository.save(city);

                        sb.append(String.format("Successfully imported city %s - %d",
                                dto.getCityName(), dto.getPopulation())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid city").append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }
}
