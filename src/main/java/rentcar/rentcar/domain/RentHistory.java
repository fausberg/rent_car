package rentcar.rentcar.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "rent_history")
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
