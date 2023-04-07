package rentcar.rentcar.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "fines")
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "f_seq")
    @SequenceGenerator(name = "f_seq", sequenceName = "fines_s",allocationSize = 1)
    private int id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "fee")
    private double fee;

    @Column(name = "description")
    private String description;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "car_id")
    private int carId;

}
