package rent_car.rent_car.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rent_car.rent_car.domain.RentHistory;
import rent_car.rent_car.repository.RentHistoryRepository;

import java.util.ArrayList;

@Service
public class RentHistoryService {

    public RentHistoryRepository rentHistoryRepository;

    @Autowired
    public RentHistoryService(RentHistoryRepository rentHistoryRepository) {
        this.rentHistoryRepository = rentHistoryRepository;
    }

    public RentHistory getRentHistoryById(int id) {
        return rentHistoryRepository.findById(id).get();
    }

    public ArrayList<RentHistory> getAllRentHistory() {
        return (ArrayList<RentHistory>) rentHistoryRepository.findAll();
    }

    public void createRentHistory(RentHistory rentHistory) {
        rentHistoryRepository.save(rentHistory);
    }

    public void updateRentHistory(RentHistory rentHistory) {
        rentHistoryRepository.saveAndFlush(rentHistory);
    }

    public void deleteRentHistory(RentHistory rentHistory) {
        rentHistoryRepository.delete(rentHistory);
    }
}
