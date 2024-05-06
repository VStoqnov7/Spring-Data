package com.example.football.repository;

import com.example.football.models.dto.PlayerExportDTO;
import com.example.football.models.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {
    Optional<Player> findByEmail(String email);
    @Query("SELECT NEW com.example.football.models.dto.PlayerExportDTO(p.firstName, p.lastName, p.position, p.team.name, p.team.stadiumName) " +
            "FROM Player p " +
            "ORDER BY p.stat.shooting DESC, p.stat.passing DESC, p.stat.endurance DESC, p.lastName")
    List<PlayerExportDTO> findPlayerByFirstNameLAndLastNameAndPositionTeamNameTeamStadiumName();

}
