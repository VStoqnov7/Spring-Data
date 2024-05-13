package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanoDTO;
import softuni.exam.models.dto.VolcanoExportDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Volcano;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class VolcanoServiceImpl implements VolcanoService {

    private final String VOLCANO_PATH = "src/main/resources/files/json/volcanoes.json";
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validation;
    private final VolcanoRepository volcanoRepository;
    private final CountryRepository countryRepository;

    public VolcanoServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validation, VolcanoRepository volcanoRepository, CountryRepository countryRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validation = validation;
        this.volcanoRepository = volcanoRepository;
        this.countryRepository = countryRepository;
    }


    @Override
    public boolean areImported() {
        return this.volcanoRepository.count() > 0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {
        return Files.readString(Path.of(VOLCANO_PATH));
    }

    @Override
    public String importVolcanoes() throws IOException {
        StringBuilder sb = new StringBuilder();

        VolcanoDTO[] volcanoDTO = gson.fromJson(readVolcanoesFileContent(),VolcanoDTO[].class);

        Arrays.stream(volcanoDTO)
                .forEach(dto-> {
                    boolean isValid = validation.isValid(dto);
                    Optional<Volcano> volcanoByName = this.volcanoRepository.findByName(dto.getName());
                    Optional<Country> countryById = this.countryRepository.findById(dto.getCountry());
                    if (isValid && !volcanoByName.isPresent() && countryById.isPresent()){
                        Volcano volcano = modelMapper.map(dto,Volcano.class);
                        volcano.setCountry(countryById.get());
                        this.volcanoRepository.save(volcano);
                        sb.append(String.format("Successfully imported volcano %s of type %s",dto.getName(),dto.getVolcanoType())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid volcano").append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }

    @Override
    public String exportVolcanoes() {
        StringBuilder sb = new StringBuilder();
        List<VolcanoExportDTO> volcano = this.volcanoRepository.findVolcano();

        volcano.forEach(dto ->{
            sb.append(String.format("Volcano: %s",dto.getName())).append(System.lineSeparator());
            sb.append(String.format("   *Located in: %s",dto.getCountryName())).append(System.lineSeparator());
            sb.append(String.format("   **Elevation: %s",dto.getElevation())).append(System.lineSeparator());
            sb.append(String.format("   ***Last eruption on: %s",dto.getLastEruption())).append(System.lineSeparator());
        });
        return sb.toString().trim();
    }
}