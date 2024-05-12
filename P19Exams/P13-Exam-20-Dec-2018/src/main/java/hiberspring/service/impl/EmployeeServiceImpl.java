package hiberspring.service.impl;

import hiberspring.domain.dtos.EmployeeExportDTO;
import hiberspring.domain.dtos.EmployeeImportContainerDTO;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Employee;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.repository.EmployeeRepository;
import hiberspring.service.EmployeeService;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static hiberspring.common.Constants.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final String EMPLOYEE_PATH = PATH_TO_FILES + "employees.xml";
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final EmployeeRepository employeeRepository;
    private final FileUtil fileUtil;
    private final BranchRepository branchRepository;
    private final EmployeeCardRepository employeeCardRepository;

    public EmployeeServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, EmployeeRepository employeeRepository, FileUtil fileUtil, BranchRepository branchRepository, EmployeeCardRepository employeeCardRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.employeeRepository = employeeRepository;
        this.fileUtil = fileUtil;
        this.branchRepository = branchRepository;
        this.employeeCardRepository = employeeCardRepository;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return fileUtil.readFile(EMPLOYEE_PATH);
    }

    @Override
    public String importEmployees() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        EmployeeImportContainerDTO employeeDTO = xmlParser.parseXml(EmployeeImportContainerDTO.class, EMPLOYEE_PATH);

        employeeDTO.getEmployees()
                .forEach(dto-> {
                    boolean isValid = validationUtil.isValid(dto);
                    Optional<Branch> branchByName = this.branchRepository.findByName(dto.getBranch());
                    Optional<EmployeeCard> cardByNumber = this.employeeCardRepository.findByNumber(dto.getCard());
                    if (isValid && branchByName.isPresent() &&  cardByNumber.isPresent()){
                        Employee employee = modelMapper.map(dto,Employee.class);
                        employee.setBranch(branchByName.get());
                        employee.setCard(cardByNumber.get());
                        this.employeeRepository.save(employee);
                        sb.append(String.format(SUCCESSFUL_IMPORT_MESSAGE,"Employee",dto.getFirstName() + " " + dto.getLastName())).append(System.lineSeparator());
                    }else {
                        sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                    }

                });
        return sb.toString().trim();
    }

    @Override
    public String exportProductiveEmployees() {
        StringBuilder sb = new StringBuilder();

        List<EmployeeExportDTO> employee = this.employeeRepository.findEmployeeByNamePositionCardNumber();

        employee.forEach(dto-> {
            sb.append(String.format("Name: %s",dto.getFirstName() + " " + dto.getLastName())).append(System.lineSeparator());
            sb.append(String.format("Position: %s",dto.getPosition())).append(System.lineSeparator());
            sb.append(String.format("Card Number: %s",dto.getEmployeeCard())).append(System.lineSeparator());
            sb.append("-------------------------").append(System.lineSeparator());
        });

        return sb.toString().trim();
    }
}
