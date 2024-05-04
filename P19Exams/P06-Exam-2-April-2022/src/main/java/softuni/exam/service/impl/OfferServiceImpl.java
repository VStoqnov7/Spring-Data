package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferExportDTO;
import softuni.exam.models.dto.OfferImportContainerDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.MyValidator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    private final String OFFER_PATH = "src/main/resources/files/xml/offers.xml";

    private final ModelMapper modelMapper;

    private final MyValidator validator;

    private final OfferRepository offerRepository;

    private final AgentRepository agentRepository;

    private final ApartmentRepository apartmentRepository;

    public OfferServiceImpl(ModelMapper modelMapper, MyValidator validator, OfferRepository offerRepository, AgentRepository agentRepository, ApartmentRepository apartmentRepository) {
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.offerRepository = offerRepository;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
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
                .forEach(dto-> {
                    boolean isValid = validator.isValid(dto);
                    Optional<Agent> agent = this.agentRepository.findByFirstName(dto.getAgent().getName());
                    Optional<Apartment> apartment = this.apartmentRepository.findById(dto.getApartment().getId());
                    if (isValid && agent.isPresent() && apartment.isPresent()){
                        Offer offer = modelMapper.map(dto,Offer.class);
                        offer.setAgent(agent.get());
                        offer.setApartment(apartment.get());
                        this.offerRepository.save(offer);
                        sb.append(String.format("Successfully imported offer %.2f",dto.getPrice())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid offer").append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }

    @Override
    public String exportOffers() {
        StringBuilder sb = new StringBuilder();

        List<OfferExportDTO> offer = this.offerRepository.findOfferByFirstNameLastNameIdAreaTownNamePrice();

        offer.forEach(dto-> {
            sb.append(String.format("Agent %s %s with offer №%d",dto.getFirstName(),dto.getLastName(),dto.getId())).append(System.lineSeparator());
            sb.append(String.format("   -Apartment area: %.2f",dto.getArea())).append(System.lineSeparator());
            sb.append(String.format("   --Town: %s",dto.getTownName())).append(System.lineSeparator());
            sb.append(String.format("   ---Price: %.2f$",dto.getPrice())).append(System.lineSeparator());
        });

        return sb.toString().trim();
    }
}
