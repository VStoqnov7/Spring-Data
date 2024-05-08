package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Passenger;
import softuni.exam.models.Plane;
import softuni.exam.models.Ticket;
import softuni.exam.models.Town;
import softuni.exam.models.dto.TicketImportContainerDTO;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;
import softuni.exam.util.ValidationUtilImpl;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    private final String TICKET_PATH = "src/main/resources/files/xml/tickets.xml";
    private final ModelMapper modelMapper;
    private final ValidationUtilImpl validationUtil;
    private final XmlParser xmlParser;
    private final TownRepository townRepository;
    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;
    private final PlaneRepository planeRepository;

    public TicketServiceImpl(ModelMapper modelMapper, ValidationUtilImpl validationUtil, XmlParser xmlParser, TownRepository townRepository, TicketRepository ticketRepository, PassengerRepository passengerRepository, PlaneRepository planeRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.townRepository = townRepository;
        this.ticketRepository = ticketRepository;
        this.passengerRepository = passengerRepository;
        this.planeRepository = planeRepository;
    }

    @Override
    public boolean areImported() {
        return this.ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(TICKET_PATH));
    }

    @Override
    public String importTickets() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        TicketImportContainerDTO ticketDTO = xmlParser.fromFile(TICKET_PATH, TicketImportContainerDTO.class);

        ticketDTO.getTickets()
                .forEach(dto-> {
                    boolean isValid = validationUtil.isValid(dto);
                    Optional<Ticket> bySerialNumber = this.ticketRepository.findBySerialNumber(dto.getSerialNumber());
                    Optional<Town> fromTown = this.townRepository.findByName(dto.getFromTown().getName());
                    Optional<Town> toTown = this.townRepository.findByName(dto.getToTown().getName());
                    Optional<Passenger> passenger = this.passengerRepository.findByEmail(dto.getPassenger().getEmail());
                    Optional<Plane> plane = this.planeRepository.findByRegisterNumber(dto.getPlane().getRegisterNumber());
                    if (isValid && !bySerialNumber.isPresent() && fromTown.isPresent() && toTown.isPresent() && passenger.isPresent() && plane.isPresent()){
                        Ticket ticket = modelMapper.map(dto,Ticket.class);
                        ticket.setFromTown(fromTown.get());
                        ticket.setToTown(toTown.get());
                        ticket.setPassenger(passenger.get());
                        ticket.setPlane(plane.get());
                        this.ticketRepository.save(ticket);
                        sb.append(String.format("Successfully imported Ticket %s - %s",dto.getFromTown().getName(),dto.getToTown().getName())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid Ticket").append(System.lineSeparator());
                    }
                });
        return sb.toString().trim();
    }
}
