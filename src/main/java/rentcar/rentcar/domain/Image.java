package rentcar.rentcar.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "i_seq")
    @SequenceGenerator(name = "i_seq", sequenceName = "images_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "driver_licence_id")
    private int driverLicenceId;

    @Column(name = "data")
    private byte[] data;
}
