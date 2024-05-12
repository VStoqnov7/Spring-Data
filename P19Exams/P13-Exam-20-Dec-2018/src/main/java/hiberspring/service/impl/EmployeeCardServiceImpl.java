package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.EmployeeCardImportDTO;
import hiberspring.domain.dtos.TownImportDTO;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.domain.entities.Town;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.service.EmployeeCardService;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static hiberspring.common.Constants.*;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {

    private final String EMPLOYEE_CARD_PATH = PATH_TO_FILES + "employee-cards.json";
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final EmployeeCardRepository employeeCardRepository;
    private final FileUtil fileUtil;

    public EmployeeCardServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, EmployeeCardRepository employeeCardRepository, FileUtil fileUtil) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.employeeCardRepository = employeeCardRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public Boolean employeeCardsAreImported() {
        return this.employeeCardRepository.count() > 0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return fileUtil.readFile(EMPLOYEE_CARD_PATH);
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) {
        StringBuilder sb = new StringBuilder();

        EmployeeCardImportDTO[] employeeCardDTO = gson.fromJson(employeeCardsFileContent,EmployeeCardImportDTO[].class);

        Arrays.stream(employeeCardDTO)
                .forEach(dto-> {
                    boolean isValid = validationUtil.isValid(dto);
                    Optional<EmployeeCard> byNumber = this.employeeCardRepository.findByNumber(dto.getNumber());
                    if (isValid && !byNumber.isPresent()){
                        EmployeeCard employeeCard = modelMapper.map(dto,EmployeeCard.class);
                        this.employeeCardRepository.save(employeeCard);
                        sb.append(String.format(SUCCESSFUL_IMPORT_MESSAGE,"Employee Card",dto.getNumber())).append(System.lineSeparator());
                    }else {
                        sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                    }
                });
        return sb.toString().trim();
    }
}
