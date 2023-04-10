package rentcar.rentcar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.Car;
import rentcar.rentcar.domain.RentHistory;
import rentcar.rentcar.domain.User;
import rentcar.rentcar.repository.RentHistoryRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class RentHistoryService {

    private final RentHistoryRepository rentHistoryRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    @Autowired
    public RentHistoryService(RentHistoryRepository rentHistoryRepository, UserService userService) {
        this.rentHistoryRepository = rentHistoryRepository;
        this.userService = userService;
    }

    public RentHistory getRentHistoryById(int id) {
        try {
            RentHistory rentHistory  = rentHistoryRepository.findById(id).get();
            if(rentHistory == null) {
                throw new NoSuchElementException();
            } else {
                return rentHistory;
            }
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        return new RentHistory();
    }

    public ArrayList<RentHistory> getAllRentHistory() {
        return (ArrayList<RentHistory>) rentHistoryRepository.findAll();
    }

    public void WriteRentHistoryStartTime(User user, Car car) {
        RentHistory rentHistory = new RentHistory();
        rentHistory.setCarId(car.getId());
        rentHistory.setUserId(user.getId());
        rentHistory.setTimeStart(new Timestamp(System.currentTimeMillis()));
        rentHistory.setCost(car.getPrice());
        rentHistoryRepository.save(rentHistory);
        user.setRentHistoryStartTime(rentHistory.getTimeStart());
        userService.updateUserTimeBooking(user);
    }

    public RentHistory WriteRentHistoryEndTime(User user) {
        RentHistory rentHistory = getRentHistoriesByTimeStart(user.getRentHistoryStartTime());
        rentHistory.setTimeEnd(new Timestamp(System.currentTimeMillis()));
        updateRentHistory(rentHistory);
        user.setRentHistoryStartTime(null);
        userService.updateUserTimeBooking(user);
        return rentHistory;
    }

    public void updateRentHistory(RentHistory rentHistory) {
        rentHistoryRepository.saveAndFlush(rentHistory);
    }

    public void deleteRentHistory(int id) {
        rentHistoryRepository.deleteById(id);
    }

    public RentHistory getRentHistoriesByTimeStart(Timestamp timeStart) {
        return rentHistoryRepository.getRentHistoriesByTimeStart(timeStart);
    }
}
