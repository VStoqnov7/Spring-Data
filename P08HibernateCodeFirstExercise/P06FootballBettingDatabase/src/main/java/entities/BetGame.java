package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bet_games")
public class BetGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @OneToOne
    private Game game;

    @Id
    @OneToOne
    private Bet bet;

    @OneToOne
    @JoinColumn
    private ResultPrediction resultPrediction;
}
