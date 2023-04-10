package rentcar.rentcar.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class FineDTO {
    private LocalDate date;

    private double fee;

    @NotBlank
    private String description;

    private int userId;

    private int carId;
}
