package rentcar.rentcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {
    @NotBlank
    private String number;

    @NotBlank
    private String color;

    @NotBlank
    private String model;

    @NotBlank
    private String manufacturer;

    private double price;

    private boolean booking;
}
