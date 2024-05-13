package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanogistsImportContainerDTO;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.entity.Volcanologist;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.repository.VolcanologistRepository;
import softuni.exam.service.VolcanologistService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class VolcanologistServiceImpl implements VolcanologistService {

    private final String VOLCANOGIST_PATH = "src/main/resources/files/xml/volcanologists.xml";
    private final ModelMapper modelMapper;
    private final ValidationUtil validation;
    private final XmlParser xmlParser;
    private final VolcanologistRepository volcanologistRepository;
    private final VolcanoRepository volcanoRepository;

    public VolcanologistServiceImpl(ModelMapper modelMapper, ValidationUtil validation, XmlParser xmlParser, VolcanologistRepository volcanologistRepository, VolcanoRepository volcanoRepository) {
        this.modelMapper = modelMapper;
        this.validation = validation;
        this.xmlParser = xmlParser;
        this.volcanologistRepository = volcanologistRepository;
        this.volcanoRepository = volcanoRepository;
    }

    @Override
    public boolean areImported() {
        return this.volcanologistRepository.count() > 0;
    }

    @Override
    public String readVolcanologistsFromFile() throws IOException {
        return Files.readString(Path.of(VOLCANOGIST_PATH));
    }

    @Override
    public String importVolcanologists() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        VolcanogistsImportContainerDTO volcanologistdDTO = xmlParser.fromFile(VOLCANOGIST_PATH, VolcanogistsImportContainerDTO.class);

        volcanologistdDTO.getVolcanologists()
                .forEach(dto-> {
                    boolean isValid = validation.isValid(dto);
                    Optional<Volcanologist> byFirstNameAndLastName = this.volcanologistRepository.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName());
                    Optional<Volcano> volcanoById = this.volcanoRepository.findById(dto.getVolcanoId());
                    if (isValid && !byFirstNameAndLastName.isPresent() && volcanoById.isPresent()){
                        Volcanologist volcanologist = modelMapper.map(dto,Volcanologist.class);
                        volcanologist.setVolcano(volcanoById.get());
                        this.volcanologistRepository.save(volcanologist);
                        sb.append(String.format("Successfully imported volcanologist %s %s",dto.getFirstName(),dto.getLastName())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid volcanologist").append(System.lineSeparator());
                    }
                });
        return sb.toString().trim();
    }
}