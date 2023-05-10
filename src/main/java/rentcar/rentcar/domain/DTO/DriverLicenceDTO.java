package rentcar.rentcar.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverLicenceDTO {
    private LocalDate issued;

    private LocalDate expire;

    private int userId;
}
