package rentcar.rentcar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.Car;
import rentcar.rentcar.domain.User;
import rentcar.rentcar.exception.RentException;
import rentcar.rentcar.repository.CarRepository;

import java.util.ArrayList;

@Service
public class CarService {

    private final CarRepository carRepository;

    private final UserService userService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final RentHistoryService rentHistoryService;

    @Autowired
    public CarService(CarRepository carRepository, UserService userService, RentHistoryService rentHistoryService) {
        this.carRepository = carRepository;
        this.userService = userService;
        this.rentHistoryService = rentHistoryService;
    }

    public Car getCarById(int id) {
        return carRepository.findById(id).get();
    }

    public ArrayList<Car> getAllCar() {
        return carRepository.getCars();
    }

    public void createCar(Car car) {
        carRepository.save(car);
    }

    public void updateCar(Car car) {
        carRepository.saveAndFlush(car);
    }

    public void deleteCar(Car car) {
        carRepository.delete(car);
    }

    public void rentCar(Integer id) {
        try {
            User user = userService.getUserByLogin();
            Car car = getCarById(id);
            if (user.getRentHistoryStartTime() == null && !car.isBooking()) {
                carRepository.bookCar(id);
                rentHistoryService.WriteRentHistoryStartTime(user, car);
            } else {
                throw new RentException("you have already rented a car");
            }
        } catch (RentException e) {
            log.error(e.getMessage());
        }
    }

    public void removeRentCar() {
        try {
            User user = userService.getUserByLogin();
            if (user.getRentHistoryStartTime() != null) {
                carRepository.removeTheBookingFromTheCar(rentHistoryService.WriteRentHistoryEndTime(user).getCarId());
            } else {
                throw new RentException("you don't have a rental car");
            }
        } catch (RentException e) {
            log.error(e.getMessage());
        }
    }
}
