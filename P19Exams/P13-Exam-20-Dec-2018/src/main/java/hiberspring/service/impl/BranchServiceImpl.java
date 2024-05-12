package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.BranchImportDTO;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Town;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.TownRepository;
import hiberspring.service.BranchService;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static hiberspring.common.Constants.*;

@Service
public class BranchServiceImpl implements BranchService {
    private final String BRANCH_PATH = PATH_TO_FILES + "branches.json";
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final BranchRepository branchRepository;
    private final TownRepository townRepository;

    private final FileUtil fileUtil;

    public BranchServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, BranchRepository branchRepository, TownRepository townRepository, FileUtil fileUtil) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.branchRepository = branchRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count() > 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return fileUtil.readFile(BRANCH_PATH);
    }

    @Override
    public String importBranches(String branchesFileContent)  {
        StringBuilder sb = new StringBuilder();

        BranchImportDTO[] branchDTO = gson.fromJson(branchesFileContent,BranchImportDTO[].class);

        Arrays.stream(branchDTO)
                .forEach(dto-> {
                    boolean isValid = validationUtil.isValid(dto);
                    Optional<Town> town = this.townRepository.findByName(dto.getTown());
                    if (isValid){
                        Branch branch = modelMapper.map(dto,Branch.class);
                        branch.setTown(town.get());
                        this.branchRepository.save(branch);
                        sb.append(String.format(SUCCESSFUL_IMPORT_MESSAGE,"Branch",dto.getName())).append(System.lineSeparator());
                    }else {
                        sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                    }
                });
        return sb.toString().trim();
    }
}
