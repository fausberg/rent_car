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
import java.time.LocalDate;

@Entity
@Data
@Table(name = "driver_licences")
@AllArgsConstructor
@NoArgsConstructor
public class DriverLicence {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dl_seq")
    @SequenceGenerator(name = "dl_seq", sequenceName = "driver_licences_s",allocationSize = 1)
    private int id;

    @Column(name = "issued")
    private LocalDate issued;

    @Column(name = "expire")
    private LocalDate expire;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "status")
    private Boolean status;
}
