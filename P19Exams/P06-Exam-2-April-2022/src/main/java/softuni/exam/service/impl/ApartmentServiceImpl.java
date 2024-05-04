package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentImportContainerDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
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
public class ApartmentServiceImpl implements ApartmentService {
    private final String APARTMENT_PATH = "src/main/resources/files/xml/apartments.xml";
    private final ModelMapper modelMapper;

    private final MyValidator validator;

    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;

    public ApartmentServiceImpl(ModelMapper modelMapper, MyValidator validator, ApartmentRepository apartmentRepository, TownRepository townRepository) {
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(APARTMENT_PATH));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(ApartmentImportContainerDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ApartmentImportContainerDTO apartmentDTO = (ApartmentImportContainerDTO) unmarshaller.unmarshal(new File(APARTMENT_PATH));
        apartmentDTO.getApartments()
                .forEach(dto-> {
                    boolean isValid = validator.isValid(dto);
                    Optional<Apartment> byTownTownNameAndArea = this.apartmentRepository.findByTownTownNameAndArea(dto.getTown(), dto.getArea());
                    if (isValid && !byTownTownNameAndArea.isPresent()){
                        Apartment apartment = modelMapper.map(dto,Apartment.class);
                        Optional<Town> town = this.townRepository.findByTownName(dto.getTown());
                        apartment.setTown(town.get());

                        this.apartmentRepository.save(apartment);
                        sb.append(String.format("Successfully imported apartment %s - %.2f",dto.getApartmentType(),dto.getArea())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid apartment").append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }
}
