package rentcar.rentcar.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FineDTO {
    private LocalDate date;

    private double fee;

    private String description;

    private int userId;

    private int carId;
}
