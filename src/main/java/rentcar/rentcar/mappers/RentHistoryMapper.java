package rentcar.rentcar.mappers;

import org.springframework.stereotype.Component;
import rentcar.rentcar.domain.DTO.RentHistoryDTO;
import rentcar.rentcar.domain.RentHistory;

@Component
public class RentHistoryMapper {

    public RentHistory mapRentHistoryDTOtoRentHistory(RentHistoryDTO rentHistoryDTO) {
        RentHistory rentHistory = new RentHistory();
        rentHistory.setTimeEnd(rentHistoryDTO.getTimeEnd());
        rentHistory.setTimeStart(rentHistoryDTO.getTimeStart());
        rentHistory.setCost(rentHistoryDTO.getCost());
        rentHistory.setUserId(rentHistoryDTO.getUserId());
        rentHistory.setCarId(rentHistoryDTO.getCarId());
        return rentHistory;
    }

    public RentHistoryDTO mapRentHistoryToRentHistoryDTO(RentHistory rentHistory) {
        RentHistoryDTO rentHistoryDTO = new RentHistoryDTO();
        rentHistoryDTO.setTimeEnd(rentHistory.getTimeEnd());
        rentHistoryDTO.setTimeStart(rentHistory.getTimeStart());
        rentHistoryDTO.setCost(rentHistory.getCost());
        rentHistoryDTO.setUserId(rentHistory.getUserId());
        rentHistoryDTO.setCarId(rentHistory.getCarId());
        return rentHistoryDTO;
    }
}
