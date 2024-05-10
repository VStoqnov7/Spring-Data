package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Seller;
import softuni.exam.models.dto.SellerImportContainerDTO;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {
    private final String SELLER_PATH = "src/main/resources/files/xml/sellers.xml";
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final SellerRepository sellerRepository;

    public SellerServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, SellerRepository sellerRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of(SELLER_PATH));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(SellerImportContainerDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        SellerImportContainerDTO sellerDTO = (SellerImportContainerDTO) unmarshaller.unmarshal(new File(SELLER_PATH));

        sellerDTO.getSellers()
                .forEach(dto-> {
                    boolean isValid = validationUtil.isValid(dto);
                    Optional<Seller> byEmail = this.sellerRepository.findByEmail(dto.getEmail());
                    if (isValid && !byEmail.isPresent()){
                        Seller seller = modelMapper.map(dto,Seller.class);
                        this.sellerRepository.save(seller);
                        sb.append(String.format("Successfully imported seller %s - %s",dto.getFirstName(),dto.getEmail())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid seller").append(System.lineSeparator());
                    }

                });
        return sb.toString().trim();
    }
}














