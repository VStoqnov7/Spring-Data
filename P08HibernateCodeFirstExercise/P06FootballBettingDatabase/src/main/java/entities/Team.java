package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    private String logo;

    @Column(length = 3)
    private String initials;

    @ManyToOne
    @JoinColumn(name = "primary_kit_color")
    private Color primaryKitColor;

    @ManyToOne
    @JoinColumn(name = "secondary_kit_color_id")
    private Color secondaryKitColor;

    @ManyToOne
    private Town town;

    private BigInteger budged;

}
