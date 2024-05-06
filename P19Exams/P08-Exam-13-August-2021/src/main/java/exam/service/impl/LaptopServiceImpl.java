package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.LaptopExportDTO;
import exam.model.dto.LaptopImportDTO;
import exam.model.entity.Laptop;
import exam.model.entity.Shop;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import exam.util.MyValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class LaptopServiceImpl implements LaptopService {
    private final String LAPTOP_PATH = "src/main/resources/files/json/laptops.json";
    private final ModelMapper modelMapper;
    private final MyValidator validator;
    private final Gson gson;
    private final LaptopRepository laptopRepository;
    private final ShopRepository shopRepository;

    public LaptopServiceImpl(ModelMapper modelMapper, MyValidator validator, Gson gson, LaptopRepository laptopRepository, ShopRepository shopRepository) {
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.gson = gson;
        this.laptopRepository = laptopRepository;
        this.shopRepository = shopRepository;
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of(LAPTOP_PATH));
    }

    @Override
    public String importLaptops() throws IOException {
        StringBuilder sb = new StringBuilder();

        LaptopImportDTO[] laptopDTO = gson.fromJson(readLaptopsFileContent(),LaptopImportDTO[].class);
        Arrays.stream(laptopDTO)
                .forEach(dto-> {
                    boolean isValid = validator.isValid(dto);
                    Optional<Laptop> byMacAddress = this.laptopRepository.findByMacAddress(dto.getMacAddress());

                    if (isValid && !byMacAddress.isPresent()){
                        Laptop laptop = modelMapper.map(dto,Laptop.class);
                        Optional<Shop> shop = this.shopRepository.findByName(dto.getShop().getName());
                        laptop.setShop(shop.get());
                        this.laptopRepository.save(laptop);
                        sb.append(String.format("Successfully imported Laptop %s - %.2f - %d %d",dto.getMacAddress(),dto.getCpuSpeed(),dto.getRam(),dto.getStorage())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid Laptop").append(System.lineSeparator());
                    }
                });
        return sb.toString().trim();
    }

    @Override
    public String exportBestLaptops() {
        StringBuilder sb = new StringBuilder();
        List<LaptopExportDTO> laptopDTO =
                this.laptopRepository.findByMacAddressCpuSpeedRamStoragePriceShopAndTownName();

        laptopDTO.forEach(dto-> {
            sb.append(String.format("Laptop - %s",dto.getMacAddress())).append(System.lineSeparator());
            sb.append(String.format("*Cpu speed  - %.2f",dto.getCpuSpeed())).append(System.lineSeparator());
            sb.append(String.format("**Ram  - %d",dto.getRam())).append(System.lineSeparator());
            sb.append(String.format("***Storage  - %d",dto.getStorage())).append(System.lineSeparator());
            sb.append(String.format("****Price  - %.2f",dto.getPrice())).append(System.lineSeparator());
            sb.append(String.format("#Shop name  - %s",dto.getShopName())).append(System.lineSeparator());
            sb.append(String.format("##Town  - %s%n",dto.getTownName())).append(System.lineSeparator());
        });
        return sb.toString().trim();
    }
}
