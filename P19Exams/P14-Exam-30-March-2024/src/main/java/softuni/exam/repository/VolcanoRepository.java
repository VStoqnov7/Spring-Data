package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.VolcanoExportDTO;
import softuni.exam.models.entity.Volcano;

import java.util.List;
import java.util.Optional;

@Repository
public interface VolcanoRepository extends JpaRepository<Volcano,Long> {

    Optional<Volcano> findByName(String name);

    @Query("SELECT NEW softuni.exam.models.dto.VolcanoExportDTO(v.name, v.country.name, v.elevation, v.lastEruption) " +
            "FROM Volcano v " +
            "WHERE v.elevation > 3000 AND v.isActive = true AND v.lastEruption IS NOT NULL " +
            "ORDER BY v.elevation DESC")
    List<VolcanoExportDTO> findVolcano();
}
