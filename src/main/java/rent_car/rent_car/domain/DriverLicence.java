package rent_car.rent_car.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "driver_licences")
public class DriverLicence {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dl_seq")
    @SequenceGenerator(name = "dl_seq", sequenceName = "driver_licences_s",allocationSize = 1)
    private int id;

    @Column(name = "issued")
    private LocalDate issued;

    @Column(name = "expire")
    private LocalDate expire;
}
