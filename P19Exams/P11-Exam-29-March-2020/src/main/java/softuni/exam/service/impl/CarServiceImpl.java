package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Car;
import softuni.exam.models.dto.CarExportDTO;
import softuni.exam.models.dto.CarImportDTO;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private final String CAR_PATH = "src/main/resources/files/json/cars.json";
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final CarRepository carRepository;

    public CarServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, CarRepository carRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.carRepository = carRepository;
    }


    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files.readString(Path.of(CAR_PATH));
    }

    @Override
    public String importCars() throws IOException {
        StringBuilder sb = new StringBuilder();

        CarImportDTO[] carDTO = gson.fromJson(readCarsFileContent(),CarImportDTO[].class);
        Arrays.stream(carDTO)
                .forEach(dto -> {
                    boolean isValid = validationUtil.isValid(dto);
                    Car car = modelMapper.map(dto, Car.class);
                    boolean exists = carRepository.findAll().stream()
                            .anyMatch(existingCar -> existingCar.equals(car));

                    if (isValid && !exists){
                        carRepository.save(car);
                        sb.append(String.format("Successfully imported car - %s - %s",dto.getMake(),dto.getModel())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid car").append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
       StringBuilder sb = new StringBuilder();

        List<CarExportDTO> cars = this.carRepository.findCarByMakeModelKilometersRegisteredOnCountPictures();

        cars.forEach(dto-> {
            sb.append(String.format("Car make - %s, model - %s",dto.getMake(),dto.getModel())).append(System.lineSeparator());
            sb.append(String.format("\tKilometers - %d",dto.getKilometers())).append(System.lineSeparator());
            sb.append(String.format("\tRegistered on - %s",dto.getRegisteredOn())).append(System.lineSeparator());
            sb.append(String.format("\tNumber of pictures - %d", dto.getCountPicture())).append(System.lineSeparator());
        });
        return sb.toString().trim();
    }
}
