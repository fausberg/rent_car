package rentcar.rentcar.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "rent_historyes")
public class RentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "rh_seq")
    @SequenceGenerator(name = "rh_seq", sequenceName = "rent_history_s", allocationSize = 1)
    private int id;

    @Column(name = "car_id")
    private int carId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "time_start")
    private Timestamp timeStart;

    @Column(name = "time_end")
    private Timestamp timeEnd;

    @Column(name = "cost")
    private double cost;
}
