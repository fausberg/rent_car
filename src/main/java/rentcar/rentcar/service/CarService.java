package rentcar.rentcar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.Car;
import rentcar.rentcar.domain.RentHistory;
import rentcar.rentcar.domain.User;
import rentcar.rentcar.repository.CarRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class CarService {

    public CarRepository carRepository;

    private final UserService userService;
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

    public void updateCarBookingStatus(Integer id) {
        carRepository.bookACar(id);
        User user = userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        Car car = getCarById(id);
        RentHistory rentHistory = new RentHistory();
        rentHistory.setCarId(id);
        rentHistory.setUserId(user.getId());
        System.out.println(LocalDateTime.now());
        rentHistory.setTimeStart(new Timestamp(System.currentTimeMillis()));
        rentHistory.setCost(car.getPrice());
        rentHistoryService.createRentHistory(rentHistory);
        user.setRentHistoryStartTime(rentHistory.getTimeStart());
        userService.updateUser(user);
    }

    public void removeTheBookingFromTheCar(Integer id) {
        carRepository.removeTheBookingFromTheCar(id);
        User user = userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        RentHistory rentHistory = rentHistoryService.getRentHistoriesByTimeStart(user.getRentHistoryStartTime());
        rentHistory.setTimeEnd(new Timestamp(System.currentTimeMillis()));
        rentHistoryService.updateRentHistory(rentHistory);
        user.setRentHistoryStartTime(null);
        userService.updateUser(user);
    }
}
