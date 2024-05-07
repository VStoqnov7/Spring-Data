package softuni.exam.instagraphlite.models.dto;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.instagraphlite.models.Posts;

import java.util.List;

@Getter
@Setter
public class UserExportDTO {
    private String username;
    private int count;
    private List<PostDetailDTO> postDetails;

    public UserExportDTO(String username, int count) {
        this.username = username;
        this.count = count;
    }
}
