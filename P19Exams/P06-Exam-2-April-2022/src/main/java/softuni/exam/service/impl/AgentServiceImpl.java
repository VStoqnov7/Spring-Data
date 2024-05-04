package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentImportDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.MyValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class AgentServiceImpl implements AgentService {
    private final String AGENT_PATH = "src/main/resources/files/json/agents.json";
    private final ModelMapper modelMapper;

    private final MyValidator validator;
    private final Gson gson;
    private final AgentRepository agentRepository;

    private final TownRepository townRepository;

    public AgentServiceImpl(ModelMapper modelMapper, MyValidator validator, Gson gson, AgentRepository agentRepository, TownRepository townRepository) {
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.gson = gson;
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(AGENT_PATH));
    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder sb = new StringBuilder();


        AgentImportDTO[] agentDTO = gson.fromJson(readAgentsFromFile(),AgentImportDTO[].class);

        Arrays.stream(agentDTO)
                .forEach(dto->{
                    boolean isValid = validator.isValid(dto);
                    Optional<Agent> byFirstName = this.agentRepository.findByFirstName(dto.getFirstName());

                    if (isValid && !byFirstName.isPresent()){
                        Agent agent = modelMapper.map(dto,Agent.class);
                        Optional<Town> town = this.townRepository.findByTownName(dto.getTown());
                        agent.setTown(town.get());
                        agentRepository.save(agent);
                        sb.append(String.format("Successfully imported agent - %s %s",dto.getFirstName(),dto.getLastName())).append(System.lineSeparator());
                    }else {
                        sb.append("Invalid agent").append(System.lineSeparator());
                    }

                });

        return sb.toString().trim();
    }
}
