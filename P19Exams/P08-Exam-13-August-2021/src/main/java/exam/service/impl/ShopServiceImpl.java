package exam.service.impl;

import exam.model.dto.ShopImportContainerDTO;
import exam.model.entity.Shop;
import exam.model.entity.Town;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
import exam.util.MyValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {

    private final String SHOP_PATH = "src/main/resources/files/xml/shops.xml";
    private final ModelMapper modelMapper;
    private final MyValidator validator;

    private final ShopRepository shopRepository;
    private final TownRepository townRepository;

    public ShopServiceImpl(ModelMapper modelMapper, MyValidator validator, ShopRepository shopRepository, TownRepository townRepository) {
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(Path.of(SHOP_PATH));
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(ShopImportContainerDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ShopImportContainerDTO shopDTO = (ShopImportContainerDTO) unmarshaller.unmarshal(new File(SHOP_PATH));

        shopDTO.getShops()
                .forEach(dto-> {
                    boolean isValid = validator.isValid(dto);
                    Optional<Shop> byName = this.shopRepository.findByName(dto.getName());

                    if (isValid && !byName.isPresent()){
                        Shop shop = modelMapper.map(dto,Shop.class);
                        Optional<Town> town = this.townRepository.findByName(dto.getTown().getName());
                        shop.setTown(town.get());
                        this.shopRepository.save(shop);
                        sb.append(String.format("Successfully imported Shop %s - %.0f",dto.getName(),dto.getIncome())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid Shop").append(System.lineSeparator());
                    }
                });
        return sb.toString().trim();
    }
}
