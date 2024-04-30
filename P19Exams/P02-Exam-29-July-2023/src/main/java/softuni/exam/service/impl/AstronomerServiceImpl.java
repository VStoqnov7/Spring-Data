package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AstronomerImportContainerDTO;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.AstronomerService;
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
public class AstronomerServiceImpl implements AstronomerService {

    private final String ASTRONOMER_XML_PATH = "src/main/resources/files/xml/astronomers.xml";
    private final AstronomerRepository astronomerRepository;

    private final MyValidator validator;
    private final ModelMapper modelMapper;

    private final StarRepository starRepository;

    @Autowired
    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, MyValidator validator, ModelMapper modelMapper, StarRepository starRepository) {
        this.astronomerRepository = astronomerRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.starRepository = starRepository;
    }

    @Override
    public boolean areImported() {
        return this.astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files
                .readString(Path.of(ASTRONOMER_XML_PATH));
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(AstronomerImportContainerDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        AstronomerImportContainerDTO containerDTO = (AstronomerImportContainerDTO) unmarshaller.unmarshal(new File(ASTRONOMER_XML_PATH));

        containerDTO.getAstronomers().stream()
                .filter(dto -> {
                    boolean isValid = validator.isValid(dto);

                    Optional<Astronomer> astronomer = this.astronomerRepository.findByFirstNameAndLastName(dto.getFirstName(),dto.getLastName());

                    if (astronomer.isPresent()){
                        isValid = false;
                    }

                    Optional<Star> star = this.starRepository.findById(dto.getObservingStarId());

                    if (star.isEmpty()){
                        isValid = false;
                    }

                    if (isValid){
                        sb.append(String.format("Successfully imported astronomer %s %s - %.2f",
                                dto.getFirstName(),dto.getLastName(),dto.getAverageObservationHours())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid astronomer").append(System.lineSeparator());
                    }

                    return isValid;
                })
                .map(dto-> modelMapper.map(dto,Astronomer.class))
                .forEach(this.astronomerRepository::save);

        return sb.toString().trim();

    }
}
