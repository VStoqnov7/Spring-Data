package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.StarExportDTO;
import softuni.exam.models.dto.StarImportDTO;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;
import softuni.exam.util.MyValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StarServiceImpl implements StarService {
    private final StarRepository starRepository;
    private final Gson gson;
    private final MyValidator validator;
    private final ModelMapper modelMapper;

    private final ConstellationRepository constellationRepository;

    @Autowired
    public StarServiceImpl(StarRepository starRepository, Gson gson, MyValidator validator, ModelMapper modelMapper, ConstellationRepository constellationRepository) {
        this.starRepository = starRepository;
        this.gson = gson;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.constellationRepository = constellationRepository;
    }

    @Override
    public boolean areImported() {
        return this.starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        String STAR_JSON_PATH = "src/main/resources/files/json/stars.json";
        return Files.readString(Path.of(STAR_JSON_PATH));
    }

    @Override
    public String importStars() throws IOException {
        StringBuilder sb = new StringBuilder();
        StarImportDTO[] constellationDTO = gson.fromJson(readStarsFileContent(), StarImportDTO[].class);

        Arrays.stream(constellationDTO)
                .filter(dto -> {
                    boolean isValid = validator.isValid(dto);

                    Optional<Star> byName = this.starRepository.findByName(dto.getName());

                    if (byName.isPresent()) {
                        isValid = false;
                    }

                    if (isValid) {
                        sb.append(String.format("Successfully imported star %s - %.2f light years",
                                dto.getName(), dto.getLightYears())).append(System.lineSeparator());
                    } else {
                        sb.append("Invalid star").append(System.lineSeparator());
                    }

                    return isValid;
                })
                .map(dto -> {
                    Star star = modelMapper.map(dto, Star.class);
                    Constellation constellation = this.constellationRepository.getById(dto.getConstellation());
                    star.setConstellation(constellation);
                    return star;
                })
                .forEach(this.starRepository::save);

        return sb.toString().trim();
    }

    @Override
    public String exportStars() {
        StringBuilder sb = new StringBuilder();
        List<Star> stars = this.starRepository.findStarByRedGiantsAndNeverObservedOrderByLightYearsAsk();

            List<StarExportDTO> starsDTO = stars.stream()
                    .map(star -> modelMapper.map(star, StarExportDTO.class))
                    .collect(Collectors.toList());

            starsDTO
                    .forEach(dto -> {
                        sb.append(String.format("Star: %s", dto.getName())).append(System.lineSeparator());
                        sb.append(String.format("   *Distance: %.2f light years", dto.getLightYears())).append(System.lineSeparator());
                        sb.append(String.format("   **Description: %s", dto.getDescription())).append(System.lineSeparator());
                        sb.append(String.format("   ***Constellation: %s", dto.getConstellationName())).append(System.lineSeparator());
                    });

        return sb.toString().trim();
    }
}
