package exam.model.dto;

import exam.util.LocalDateAdapterGson;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Getter
@Setter
public class CustomerImportDTO {

    @NotNull
    @Size(min = 2)
    private String firstName;

    @NotNull
    @Size(min = 2)
    private String lastName;

    @NotNull
    @Pattern(regexp = ".*@.*\\..*")
    private String email;

    @NotNull
    private LocalDate registeredOn;

    private CustomerTownNameDTO town;
}
