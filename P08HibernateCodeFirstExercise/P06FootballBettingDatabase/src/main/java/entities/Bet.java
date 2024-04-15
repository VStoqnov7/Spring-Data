package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "bets")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bet_money")
    private double betMoney;

    @Column(name = "date_time")
    private LocalDate dateTime;

    @ManyToOne
    private User user;
}
