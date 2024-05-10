package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Car;
import softuni.exam.models.Offer;
import softuni.exam.models.Picture;
import softuni.exam.models.Seller;
import softuni.exam.models.dto.OfferImportContainerDTO;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.OfferService;
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
public class OfferServiceImpl implements OfferService {
    private final String OFFER_PATH = "src/main/resources/files/xml/offers.xml";
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final OfferRepository offerRepository;

    private final CarRepository carRepository;
    private final SellerRepository sellerRepository;

    public OfferServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, OfferRepository offerRepository, CarRepository carRepository, SellerRepository sellerRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.offerRepository = offerRepository;
        this.carRepository = carRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFER_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(OfferImportContainerDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        OfferImportContainerDTO offerDTO = (OfferImportContainerDTO) unmarshaller.unmarshal(new File(OFFER_PATH));

        offerDTO.getOffers()
                .forEach(dto -> {
                    boolean isValid = validationUtil.isValid(dto);
                    Optional<Car> car = this.carRepository.findById(dto.getCar().getId());
                    Optional<Seller> seller = this.sellerRepository.findById(dto.getSeller().getId());
                    Offer offer = modelMapper.map(dto,Offer.class);
                    boolean exists = offerRepository.findAll().stream()
                            .anyMatch(existingOffer -> existingOffer.equals(offer));
                    if (isValid && car.isPresent() && seller.isPresent() && !exists){
                        offer.setCar(car.get());
                        offer.setSeller(seller.get());
                        this.offerRepository.save(offer);
//                        String hasGoldStatus = dto.isHasGoldStatus() ? "true" : "False";
                        sb.append(String.format("Successfully imported offer - %s - %s",dto.getAddedOn(),dto.isHasGoldStatus())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid offer").append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }
}
