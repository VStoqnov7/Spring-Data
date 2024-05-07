package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.Pictures;
import softuni.exam.instagraphlite.models.dto.PictureExportDTO;
import softuni.exam.instagraphlite.models.dto.PictureImportDTO;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.MyValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PictureServiceImpl implements PictureService {
    private final String PICTURE_PATH = "src/main/resources/files/pictures.json";
    private final ModelMapper modelMapper;
    private final MyValidator validator;

    private final Gson gson;
    private final PictureRepository pictureRepository;

    public PictureServiceImpl(ModelMapper modelMapper, MyValidator validator, Gson gson, PictureRepository pictureRepository) {
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.gson = gson;
        this.pictureRepository = pictureRepository;
    }


    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(PICTURE_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();

        PictureImportDTO[] pictureDTO = gson.fromJson(readFromFileContent(),PictureImportDTO[].class);
        Arrays.stream(pictureDTO)
                .forEach(dto-> {
                    boolean isValid = validator.isValid(dto);
                    Optional<Pictures> byPath = this.pictureRepository.findByPath(dto.getPath());
                    if (isValid && !byPath.isPresent()){
                        Pictures pictures = modelMapper.map(dto,Pictures.class);
                        this.pictureRepository.save(pictures);
                        sb.append(String.format("Successfully imported Picture, with size %.2f",dto.getSize())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid Picture").append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }

    @Override
    public String exportPictures() {
        StringBuilder sb = new StringBuilder();
        List<PictureExportDTO> picture = this.pictureRepository.findPicturesByPathAndSize();
        picture.forEach(dto->{
            sb.append(String.format("%.2f - %s",dto.getSize(),dto.getPath())).append(System.lineSeparator());
        });
        return sb.toString().trim();
    }
}
