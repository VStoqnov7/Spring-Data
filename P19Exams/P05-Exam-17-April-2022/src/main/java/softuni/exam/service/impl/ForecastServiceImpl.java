package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ForecastExportDTO;
import softuni.exam.models.dto.ForecastImportContainerDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.MyValidator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final String FORECAST_PATH = "src/main/resources/files/xml/forecasts.xml";

    private final ForecastRepository forecastRepository;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    private final MyValidator validator;


    public ForecastServiceImpl(ForecastRepository forecastRepository, CityRepository cityRepository, ModelMapper modelMapper, MyValidator validator) {
        this.forecastRepository = forecastRepository;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }


    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(FORECAST_PATH));
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(ForecastImportContainerDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ForecastImportContainerDTO forecastDTO = (ForecastImportContainerDTO) unmarshaller.unmarshal(new File(FORECAST_PATH));
        forecastDTO.getForecasts().forEach(dto-> {
            boolean isValid = validator.isValid(dto);

            Optional<Forecast> existingForecast = this.forecastRepository
                    .findByDayOfWeekAndCityId(dto.getDayOfWeek(), dto.getCity());

            if (existingForecast.isPresent() || !isValid) {
                sb.append("Invalid forecast").append(System.lineSeparator());
            } else {
                Optional<City> city = cityRepository.findById(dto.getCity());
                Forecast forecast = modelMapper.map(dto, Forecast.class);
                forecast.setCity(city.get());
                this.forecastRepository.save(forecast);
                sb.append(String.format("Successfully import forecast %s - %.2f",
                        dto.getDayOfWeek(), dto.getMaxTemperature())).append(System.lineSeparator());
            }
        });
        return sb.toString().trim();
    }

    @Override
    public String exportForecasts() {
        StringBuilder sb = new StringBuilder();
        List<ForecastExportDTO> dtoExport = this.forecastRepository.findByCityNameMinAndMaxTemperatureSunriseSunset();

        dtoExport.forEach(dto-> {
            sb.append(String.format("City: %s",dto.getCityName())).append(System.lineSeparator());
            sb.append(String.format("-min temperature: %.2f",dto.getMinTemperature())).append(System.lineSeparator());
            sb.append(String.format("--min temperature: %.2f",dto.getMaxTemperature())).append(System.lineSeparator());
            sb.append(String.format("---min sunrise: %s",dto.getSunrise())).append(System.lineSeparator());
            sb.append(String.format("----min sunset: %s",dto.getSunset())).append(System.lineSeparator());
        });


        return sb.toString().trim();
    }
}
