package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "player_statistics")
public class PlayerStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Id
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "scored_goals")
    private short scoredGoals;

    @Column(name = "player_assists")
    private short playerAssists;

    @Column(name = "played_minutes")
    private short playedMinutes;
}
