package entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "swift_code")
    private String swiftCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType billingType;

    public BankAccount() {
    }
}
