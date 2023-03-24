package rentcar.rentcar.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "c_seq")
    @SequenceGenerator(name = "c_seq", sequenceName = "cars_s", allocationSize = 1)
    private int id;

    @Column(name = "number")
    private String number;

    @Column(name = "color")
    private String color;

    @Column(name = "model")
    private String model;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "price")
    private double price;

    @Column(name = "booking")
    private boolean booking;
}
