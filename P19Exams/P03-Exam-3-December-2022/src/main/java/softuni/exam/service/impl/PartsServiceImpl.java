package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PartImportDTO;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartsRepository;
import softuni.exam.service.PartsService;
import softuni.exam.util.MyValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class PartsServiceImpl implements PartsService {

    private static String PARTS_JSON_PATH = "src/main/resources/files/json/parts.json";
    private final PartsRepository partRepository;
    private final Gson gson;
    private final MyValidator validator;

    private final ModelMapper modelMapper;

    @Autowired
    public PartsServiceImpl(PartsRepository partRepository, Gson gson, MyValidator validator, ModelMapper modelMapper) {
        this.partRepository = partRepository;
        this.gson = gson;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.partRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(Path.of(PARTS_JSON_PATH));
    }

    @Override
    public String importParts() throws IOException {
        StringBuilder sb = new StringBuilder();

        PartImportDTO[] partDTO = gson.fromJson(readPartsFileContent(),PartImportDTO[].class);

        Arrays.stream(partDTO)
                .filter(dto-> {
                    boolean isValid = validator.isValid(dto);

                    Optional<Part> partName = this.partRepository.findByPartName(dto.getPartName());
                    if (partName.isPresent()){
                        isValid = false;
                    }

                    if (isValid){
                        sb.append(String.format("Successfully imported part %s - %.2f",
                                dto.getPartName(),dto.getPrice())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid part").append(System.lineSeparator());
                    }


                    return isValid;
                })
                .map(dto-> modelMapper.map(dto,Part.class))
                .forEach(part -> partRepository.save(part));

        return sb.toString().trim();
    }
}
