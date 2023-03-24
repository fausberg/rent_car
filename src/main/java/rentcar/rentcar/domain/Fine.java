package rentcar.rentcar.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

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



}
