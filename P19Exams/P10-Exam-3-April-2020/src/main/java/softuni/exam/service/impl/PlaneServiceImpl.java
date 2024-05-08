package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Plane;
import softuni.exam.models.dto.PlaneImportContainerDTO;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.ValidationUtilImpl;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class PlaneServiceImpl implements PlaneService {
    private final String PLANE_PATH = "src/main/resources/files/xml/planes.xml";
    private final ModelMapper modelMapper;
    private final ValidationUtilImpl validationUtil;

    private final XmlParser xmlParser;
    private final PlaneRepository planeRepository;

    public PlaneServiceImpl(ModelMapper modelMapper, ValidationUtilImpl validationUtil, XmlParser xmlParser, PlaneRepository planeRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.planeRepository = planeRepository;
    }

    @Override
    public boolean areImported() {
        return this.planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(Path.of(PLANE_PATH));
    }

    @Override
    public String importPlanes() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        PlaneImportContainerDTO planeDTO = xmlParser.fromFile(PLANE_PATH, PlaneImportContainerDTO.class);

        planeDTO.getPlanes()
                .forEach(dto -> {
                    boolean isValid = validationUtil.isValid(dto);
                    Optional<Plane> byRegisterNumber = this.planeRepository.findByRegisterNumber(dto.getRegisterNumber());
                    if (isValid && !byRegisterNumber.isPresent()){
                        Plane plane = modelMapper.map(dto,Plane.class);
                        this.planeRepository.save(plane);
                        sb.append(String.format("Successfully imported Plane %s",dto.getRegisterNumber())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid Plane").append(System.lineSeparator());
                    }
                });
        return sb.toString().trim();
    }
}
