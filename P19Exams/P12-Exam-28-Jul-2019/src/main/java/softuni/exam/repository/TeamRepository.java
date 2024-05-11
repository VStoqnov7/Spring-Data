package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.domain.entities.Team;
import softuni.exam.domain.entities.dto.PlayerExportDTO;
import softuni.exam.domain.entities.dto.TeamNameAndPlayerExportDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team,Integer> {

    Optional<Team> findByName(String name);

    @Query("SELECT NEW softuni.exam.domain.entities.dto.TeamNameAndPlayerExportDTO(t.name) " +
            "FROM Team t " +
            "WHERE t.name = 'North Hub'")
    Optional<TeamNameAndPlayerExportDTO> findByNameNorthHub();

}
