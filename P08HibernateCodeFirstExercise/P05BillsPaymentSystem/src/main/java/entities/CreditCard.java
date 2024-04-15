package entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "credit_cards")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "expiration_month", nullable = false)
    private int expirationMonth;

    @Column(name = "expiration_year")
    private int expirationYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType billingType;
    public CreditCard() {
    }
}
