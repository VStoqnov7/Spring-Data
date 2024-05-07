package softuni.exam.instagraphlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.Posts;
import softuni.exam.instagraphlite.models.dto.PostDetailDTO;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Posts,Integer> {
    @Query("SELECT NEW softuni.exam.instagraphlite.models.dto.PostDetailDTO(p.caption, p.picture.size) " +
            "FROM Posts p " +
            "WHERE p.user.username = :user " +
            "ORDER BY p.picture.size")
    List<PostDetailDTO> findPostsByUser(@Param("user") String user);

}
