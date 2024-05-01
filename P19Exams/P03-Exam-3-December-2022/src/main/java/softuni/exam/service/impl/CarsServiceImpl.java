package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarImportContainerDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarsRepository;
import softuni.exam.service.CarsService;
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
public class CarsServiceImpl implements CarsService {
    private static String CARS_FILE_PATH = "src/main/resources/files/xml/cars.xml";

    private final CarsRepository carRepository;
    private final MyValidator validator;

    private final ModelMapper modelMapper;

    @Autowired
    public CarsServiceImpl(CarsRepository carRepository, MyValidator validator, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        String ss = Files.readString(Path.of(CARS_FILE_PATH));
        return ss;
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(CarImportContainerDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        CarImportContainerDTO carDTO = (CarImportContainerDTO) unmarshaller.unmarshal(new File(CARS_FILE_PATH));

        carDTO.getCars()
                .stream()
                .filter(dto-> {
                    boolean isValid = validator.isValid(dto);

                    Optional<Car> byPlateNumber = this.carRepository.findByPlateNumber(dto.getPlateNumber());
                    if (byPlateNumber.isPresent()){
                        isValid = false;
                    }

                    if (isValid){
                        sb.append(String.format("Successfully imported car %s - %s",
                                dto.getCarMake(),dto.getCarModel())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid car").append(System.lineSeparator());
                    }

                    return isValid;
                })
                .map(dto-> modelMapper.map(dto,Car.class))
                .forEach(car -> this.carRepository.save(car));

        return sb.toString().trim();

    }
}
