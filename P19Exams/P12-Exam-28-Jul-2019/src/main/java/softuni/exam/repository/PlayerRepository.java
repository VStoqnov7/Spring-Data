package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.dto.PlayerExportBiggerThanDTO;
import softuni.exam.domain.entities.dto.PlayerExportDTO;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {

    @Query("SELECT NEW softuni.exam.domain.entities.dto.PlayerExportDTO(p.firstName, p.lastName, p.position, p.number) " +
            "FROM Player p " +
            "WHERE p.team.name = 'North Hub'" +
            "ORDER BY p.id")
    List<PlayerExportDTO> findByTeamName();


    @Query("SELECT NEW softuni.exam.domain.entities.dto.PlayerExportBiggerThanDTO(p.firstName, p.lastName, p.number, p.salary, p.team.name) " +
            "FROM Player p " +
            "WHERE p.salary > 100000 " +
            "ORDER BY p.salary DESC")
    List<PlayerExportBiggerThanDTO> findByFirstLastNameNumberSalaryTeamName();


}
