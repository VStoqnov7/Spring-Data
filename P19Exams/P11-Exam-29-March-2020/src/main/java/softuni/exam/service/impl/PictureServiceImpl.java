package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Car;
import softuni.exam.models.Picture;
import softuni.exam.models.dto.PictureImportDTO;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class PictureServiceImpl implements PictureService {
    private final String PICTURE_PATH = "src/main/resources/files/json/pictures.json";
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final PictureRepository pictureRepository;

    private final CarRepository carRepository;

    public PictureServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, PictureRepository pictureRepository, CarRepository carRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.pictureRepository = pictureRepository;
        this.carRepository = carRepository;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files.readString(Path.of(PICTURE_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();

        PictureImportDTO[] pictureDTO = gson.fromJson(readPicturesFromFile(),PictureImportDTO[].class);

        Arrays.stream(pictureDTO)
                .forEach(dto-> {
                    boolean isValid = validationUtil.isValid(dto);
                    Optional<Picture> byName = this.pictureRepository.findByName(dto.getName());
                    if (isValid && !byName.isPresent()){
                        Picture picture = modelMapper.map(dto,Picture.class);
                        Optional<Car> car = this.carRepository.findById(dto.getCar());
                        picture.setCar(car.get());
                        this.pictureRepository.save(picture);
                        sb.append(String.format("Successfully imported picture - %s",dto.getName())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid picture").append(System.lineSeparator());
                    }
                });
        return sb.toString().trim();
    }
}
