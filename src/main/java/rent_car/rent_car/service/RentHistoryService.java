package rentcar.rentcar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.RentHistory;
import rentcar.rentcar.repository.RentHistoryRepository;

import java.util.ArrayList;

@Service
public class RentHistoryService {

    public RentHistoryRepository rentHistoryRepository;

    @Autowired
    public RentHistoryService(RentHistoryRepository rentHistoryRepository) {
        this.rentHistoryRepository = rentHistoryRepository;
    }

    public RentHistory getRentHistoryById(int id) {
        return rentHistoryRepository.getRentHistoryById(id);
    }

    public ArrayList<RentHistory> getAllRentHistory() {
        return rentHistoryRepository.getAllRentHistory();
    }

    public void createRentHistory(RentHistory rentHistory) {
        rentHistoryRepository.createRentHistory(rentHistory);
    }

    public void updateRentHistory(RentHistory rentHistory) {
        rentHistoryRepository.updateRentHistory(rentHistory);
    }

    public void deleteRentHistory(RentHistory rentHistory) {
        rentHistoryRepository.deleteRentHistory(rentHistory);
    }
}
