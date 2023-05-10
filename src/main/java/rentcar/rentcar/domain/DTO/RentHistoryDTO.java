package rentcar.rentcar.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentHistoryDTO {
    private Timestamp timeStart;

    private Timestamp timeEnd;

    private double cost;

    private int carId;

    private int userId;
}
