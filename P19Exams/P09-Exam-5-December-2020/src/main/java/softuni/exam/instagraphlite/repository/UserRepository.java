package softuni.exam.instagraphlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.Posts;
import softuni.exam.instagraphlite.models.Users;
import softuni.exam.instagraphlite.models.dto.PostDetailDTO;
import softuni.exam.instagraphlite.models.dto.UserExportDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
    Optional<Users> findByUsername(String username);

    @Query("SELECT NEW softuni.exam.instagraphlite.models.dto.UserExportDTO(u.username, size(u.posts)) " +
            "FROM Users u " +
            "ORDER BY size(u.posts) DESC, u.id")
    List<UserExportDTO> findUsersByUsernameCountPosts();


}
