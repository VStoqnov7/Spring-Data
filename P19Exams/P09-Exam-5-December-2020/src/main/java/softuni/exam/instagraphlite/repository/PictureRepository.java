package softuni.exam.instagraphlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.Pictures;
import softuni.exam.instagraphlite.models.dto.PictureExportDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Pictures,Integer> {

    Optional<Pictures> findByPath(String path);

    @Query("SELECT NEW softuni.exam.instagraphlite.models.dto.PictureExportDTO(p.path, p.size) " +
            "FROM Pictures p " +
            "WHERE p.size > 30000" +
            "ORDER BY p.size")
    List<PictureExportDTO> findPicturesByPathAndSize();
}
