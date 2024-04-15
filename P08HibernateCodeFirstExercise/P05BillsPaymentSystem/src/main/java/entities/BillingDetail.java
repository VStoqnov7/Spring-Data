package entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "billing_details")
public class BillingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int number;

    @OneToOne
    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;

    @OneToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    public BillingDetail() {
    }
}
