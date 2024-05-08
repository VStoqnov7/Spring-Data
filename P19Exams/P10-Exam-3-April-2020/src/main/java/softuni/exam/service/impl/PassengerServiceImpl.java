package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Passenger;
import softuni.exam.models.Town;
import softuni.exam.models.dto.PassengerExportDTO;
import softuni.exam.models.dto.PassengerImportDTO;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.util.ValidationUtilImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final String PASSENGER_PATH = "src/main/resources/files/json/passengers.json";
    private final ModelMapper modelMapper;
    private final ValidationUtilImpl validationUtil;
    private final PassengerRepository passengerRepository;
    private final TownRepository townRepository;
    private final Gson gson;

    public PassengerServiceImpl(ModelMapper modelMapper, ValidationUtilImpl validationUtil, PassengerRepository passengerRepository, TownRepository townRepository, Gson gson) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.passengerRepository = passengerRepository;
        this.townRepository = townRepository;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(PASSENGER_PATH));
    }

    @Override
    public String importPassengers() throws IOException {
        StringBuilder sb = new StringBuilder();

        PassengerImportDTO[] passengerDTO = gson.fromJson(readPassengersFileContent(),PassengerImportDTO[].class);
        Arrays.stream(passengerDTO)
                .forEach(dto-> {
                    boolean isValid = validationUtil.isValid(dto);
                    Optional<Passenger> byEmail = this.passengerRepository.findByEmail(dto.getEmail());

                    if (isValid && !byEmail.isPresent()){
                        Passenger passenger = modelMapper.map(dto,Passenger.class);
                        Optional<Town> town = townRepository.findByName(dto.getTown());
                        passenger.setTown(town.get());
                        passengerRepository.save(passenger);
                        sb.append(String.format("Successfully imported Passenger %s - %s",dto.getFirstName(),dto.getEmail())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid Passenger").append(System.lineSeparator());
                    }
                });

        return sb.toString().trim();
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        StringBuilder sb = new StringBuilder();

        List<PassengerExportDTO> passengers = this.passengerRepository.findPassengerByFirstNameAndLastNameAndEmailAndPhoneNumber();

        passengers.forEach(dto-> {
            sb.append(String.format("Passenger %s %s",dto.getFirstName(),dto.getLastName())).append(System.lineSeparator());
            sb.append(String.format("   Email - %s",dto.getEmail())).append(System.lineSeparator());
            sb.append(String.format("   Phone - %s",dto.getPhoneNumber())).append(System.lineSeparator());
            sb.append(String.format("   Number of tickets - %d",dto.getCountTickets())).append(System.lineSeparator());
        });

        return sb.toString().trim();
    }
}
