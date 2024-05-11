package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.dto.PictureImportContainerDTO;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.PictureService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

@Service
public class PictureServiceImpl implements PictureService {

    private final String PICTURE_PATH = "src/main/resources/files/xml/pictures.xml";
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;
    private final PictureRepository pictureRepository;

    public PictureServiceImpl(ModelMapper modelMapper, ValidatorUtil validatorUtil, FileUtil fileUtil, PictureRepository pictureRepository) {
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public String importPictures() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(PictureImportContainerDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        PictureImportContainerDTO pictureDTO = (PictureImportContainerDTO) unmarshaller.unmarshal(new File(PICTURE_PATH));

        pictureDTO.getPictures()
                .forEach(dto-> {
                    boolean isValid = validatorUtil.isValid(dto);
                    if (isValid){
                        Picture picture = modelMapper.map(dto,Picture.class);
                        this.pictureRepository.save(picture);
                        sb.append(String.format("Successfully imported picture - %s",dto.getUrl())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid picture").append(System.lineSeparator());
                    }
                });
        return sb.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return fileUtil.readFile(PICTURE_PATH);
    }

}
