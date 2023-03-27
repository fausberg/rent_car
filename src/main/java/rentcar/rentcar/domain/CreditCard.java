package rentcar.rentcar.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
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
