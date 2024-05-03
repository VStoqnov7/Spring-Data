package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PersonImportDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Person;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.PersonRepository;
import softuni.exam.service.PersonService;
import softuni.exam.util.MyValidator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final String PERSON_PATH = "src/main/resources/files/json/people.json";
    private final ModelMapper modelMapper;

    private final MyValidator validator;

    private final Gson gson;

    private final PersonRepository personRepository;

    private final CountryRepository countryRepository;

    public PersonServiceImpl(ModelMapper modelMapper, MyValidator validator, Gson gson, PersonRepository personRepository, CountryRepository countryRepository) {
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.gson = gson;
        this.personRepository = personRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return this.personRepository.count() > 0;
    }

    @Override
    public String readPeopleFromFile() throws IOException {
        return Files.readString(Path.of(PERSON_PATH));
    }

    @Override
    public String importPeople() throws IOException {
        StringBuilder sb = new StringBuilder();

        PersonImportDTO[] personDTO = gson.fromJson(readPeopleFromFile(),PersonImportDTO[].class);

        Arrays.stream(personDTO)
                .filter(dto->{
                    boolean isValid = validator.isValid(dto);

                    Optional<Person> byFirstName = this.personRepository.findByFirstName(dto.getFirstName());
                    Optional<Person> byEmail = this.personRepository.findByEmail(dto.getEmail());
                    Optional<Person> byPhone = this.personRepository.findByPhone(dto.getPhone());
                    if (byFirstName.isPresent() || byEmail.isPresent() || byPhone.isPresent()){
                        isValid = false;
                    }

                    if (isValid){
                        sb.append(String.format("Successfully imported person %s %s",
                                dto.getFirstName(),dto.getLastName())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid person").append(System.lineSeparator());
                    }
                    return isValid;
                })
                .map(dto-> {
                    Person person = modelMapper.map(dto,Person.class);
                    Country country = countryRepository.findById(dto.getCountry()).orElse(null);
                    person.setCountry(country);
                    return person;
                }).forEach(person -> this.personRepository.save(person));

        return sb.toString().trim();
    }
}
