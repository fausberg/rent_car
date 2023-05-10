package rentcar.rentcar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credit_cards")
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cr_seq")
    @SequenceGenerator(name = "cr_seq", sequenceName = "credit_card_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "date")
    private String date;

    @Column(name = "cvv")
    private String CVV;

    @Column(name = "user_id")
    private int userId;
}
