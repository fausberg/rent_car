package rentcar.rentcar.domain.DTO;

import lombok.Data;

@Data
public class CarDTO {
    private String number;

    private String color;

    private String model;

    private String manufacturer;

    private double price;

    private boolean booking;
}
