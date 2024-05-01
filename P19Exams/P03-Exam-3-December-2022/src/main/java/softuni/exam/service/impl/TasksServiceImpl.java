package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TaskExportDTO;
import softuni.exam.models.dto.TaskImportContainerDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.models.entity.Task;
import softuni.exam.repository.CarsRepository;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.repository.PartsRepository;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.TasksService;
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
public class TasksServiceImpl implements TasksService {
    private static String TASKS_FILE_PATH = "src/main/resources/files/xml/tasks.xml";
    private final TasksRepository taskRepository;

    private final MyValidator validator;

    private final ModelMapper modelMapper;

    private final MechanicsRepository mechanicRepository;

    private final CarsRepository carRepository;


    private final PartsRepository partRepository;

    @Autowired
    public TasksServiceImpl(TasksRepository taskRepository, MyValidator validator, ModelMapper modelMapper, MechanicsRepository mechanicRepository, CarsRepository carRepository, PartsRepository partRepository) {
        this.taskRepository = taskRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.mechanicRepository = mechanicRepository;
        this.carRepository = carRepository;
        this.partRepository = partRepository;
    }

    @Override
    public boolean areImported() {
        return this.taskRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(Path.of(TASKS_FILE_PATH));
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(TaskImportContainerDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        TaskImportContainerDTO taskDTO = (TaskImportContainerDTO) unmarshaller.unmarshal(new File(TASKS_FILE_PATH));

        taskDTO.getTasks()
                .stream()
                .filter(dto-> {
                    boolean isValid = validator.isValid(dto);

                    String mechanicFirstName = dto.getMechanic().getFirstName();

                    Optional<Mechanic> byMechanicFirstName = this.mechanicRepository.findByFirstName(mechanicFirstName);

                    if (byMechanicFirstName.isEmpty()){
                        isValid = false;
                    }

                    if (isValid){
                        sb.append(String.format("Successfully imported task %.2f",
                                dto.getPrice())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid task").append(System.lineSeparator());
                    }

                    return isValid;
                })
                .forEach(taskDTOs -> {
                    Task task = modelMapper.map(taskDTOs, Task.class);
                    Optional<Car> carOptional = carRepository.findById(taskDTOs.getCar().getId());
                    Optional<Mechanic> mechanicOptional = mechanicRepository.findByFirstName(taskDTOs.getMechanic().getFirstName());
                    Optional<Part> partOptional = partRepository.findById(taskDTOs.getPart().getId());

                    if (carOptional.isPresent() && mechanicOptional.isPresent() && partOptional.isPresent()) {
                        task.setCar(carOptional.get());
                        task.setMechanic(mechanicOptional.get());
                        task.setPart(partOptional.get());
                        taskRepository.save(task);
                    }
                });

        return sb.toString().trim();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        List<Task> highestPricedTasks = taskRepository.findByOrderByPriceDesc();

        StringBuilder sb = new StringBuilder();
        for (Task task : highestPricedTasks) {
            TaskExportDTO taskExportDTO = modelMapper.map(task, TaskExportDTO.class);

            sb.append(String.format("Car %s %s with %skm%n", taskExportDTO.getCarMake(), taskExportDTO.getCarModel(), taskExportDTO.getCarKilometers()))
                    .append(String.format("-Mechanic: %s %s - task №%d:%n", taskExportDTO.getMechanicFirstName(), taskExportDTO.getMechanicLastName(), taskExportDTO.getId()))
                    .append(String.format(" --Engine: %s%n", taskExportDTO.getCarEngine()))
                    .append(String.format("---Price: %.2f$%n", taskExportDTO.getPrice()));
        }
        return sb.toString();
    }
}
