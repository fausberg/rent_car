package rentcar.rentcar.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "rent_historyes")
public class RentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "rh_seq", sequenceName = "rent_history_s", allocationSize = 1)
    private int id;

//    @Column(name = "car_id")
//    private Car car;
//
//    @Column(name = "user_id")
//    private  User user;

    @Column(name = "start")
    private LocalDate start;

    @Column(name = "end")
    private LocalDate end;

    @Column(name = "cost")
    private double cost;
}
