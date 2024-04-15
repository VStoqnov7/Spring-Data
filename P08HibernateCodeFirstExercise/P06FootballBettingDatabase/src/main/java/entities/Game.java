package entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "home_team")
    private Team homeTeam;

    @OneToOne
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    @Column(name = "home_team_goals")
    private short homeTeamGoals;

    @Column(name = "away_team_goals")
    private short awayTeamGoals;

    @Column(name = "date_time")
    private LocalDate dateTime;

    @Column(name = "home_team_win_bet_rate")
    private double homeTeamWinBetRate;

    @Column(name = "away_team_win_bet_rate")
    private double awayTeamWinBetRate;

    @Column(name = "draw_game_bet_rate")
    private double drawGameBetRate;

    @ManyToOne
    @JoinColumn
    private Round round;

    @ManyToOne
    @JoinColumn
    private Competition competition;

}
