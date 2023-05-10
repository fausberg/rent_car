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
            RentHistory rentHistory = rentHistoryRepository.findById(id).get();
            if (rentHistory == null) {
                throw new NoSuchElementException();
            }
            return rentHistory;
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        return new RentHistory();
    }

    public ArrayList<RentHistory> getAllRentHistory() {
        return (ArrayList<RentHistory>) rentHistoryRepository.findAll();
    }

    public void writeRentHistoryStartTime(User user, Car car) {
        RentHistory rentHistory = new RentHistory();
        rentHistory.setCarId(car.getId());
        rentHistory.setUserId(user.getId());
        rentHistory.setTimeStart(new Timestamp(System.currentTimeMillis()));
        rentHistory.setCost(car.getPrice());
        rentHistoryRepository.save(rentHistory);
        user.setRentHistoryStartTime(rentHistory.getTimeStart());
        userService.updateUserTimeBooking(user);
    }

    public RentHistory writeRentHistoryEndTime(User user) {
        RentHistory rentHistory = getRentHistoriesByTimeStart(user.getRentHistoryStartTime());
        rentHistory.setTimeEnd(new Timestamp(System.currentTimeMillis()));
        updateRentHistory(rentHistory);
        user.setRentHistoryStartTime(null);
        userService.updateUserTimeBooking(user);
        return rentHistory;
    }

    public boolean updateRentHistory(RentHistory rentHistory) {
        ArrayList<RentHistory> rentHistories = getAllRentHistory();
        for (RentHistory rentHistoryOfList : rentHistories) {
            if (rentHistoryOfList.getTimeStart() == rentHistory.getTimeStart() && rentHistoryOfList.getTimeEnd() == rentHistory.getTimeEnd()) {
                rentHistory.setId(rentHistoryOfList.getId());
                rentHistoryRepository.saveAndFlush(rentHistory);
                return true;
            }
        }
        log.error("rent history not found");
        return false;
    }

    public boolean deleteRentHistory(int id) {
        ArrayList<RentHistory> rentHistories = getAllRentHistory();
        for (RentHistory rentHistory : rentHistories) {
            if (rentHistory.getId() == id) {
                rentHistoryRepository.deleteById(id);
                return true;
            }
        }
        log.error("rent history not found");
        return false;
    }

    public RentHistory getRentHistoriesByTimeStart(Timestamp timeStart) {
        return rentHistoryRepository.getRentHistoriesByTimeStart(timeStart);
    }
}
