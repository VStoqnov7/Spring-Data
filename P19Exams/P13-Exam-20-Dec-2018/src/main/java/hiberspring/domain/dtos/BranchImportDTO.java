package hiberspring.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BranchImportDTO {

    @NotNull
    private String name;

    @NotNull
    private String town;
}
