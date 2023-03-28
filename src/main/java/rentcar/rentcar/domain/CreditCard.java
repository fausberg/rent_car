package rentcar.rentcar.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Data
@Table(name = "credit_cards")
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cr_seq")
    @SequenceGenerator(name = "cr_seq", sequenceName = "credit_card_id_seq", allocationSize = 1)
    private int id;

    @NotBlank
    @Pattern(regexp = "[0-9]{16}")
    @Column(name = "card_number")
    private String cardNumber;

    @NotBlank
    @Pattern(regexp = "(0[1-9]|1[0-2])/([0-9]{2})")
    @Column(name = "date")
    private String date;

    @NotBlank
    @Pattern(regexp = "[0-9]{3}")
    @Column(name = "cvv")
    private String CVV;

    @Column(name = "user_id")
    private int userId;
}
