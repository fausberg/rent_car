package rentcar.rentcar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.Car;
import rentcar.rentcar.domain.DriverLicence;
import rentcar.rentcar.domain.User;
import rentcar.rentcar.exception.RentException;
import rentcar.rentcar.repository.CarRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class CarService {

    private final CarRepository carRepository;

    private final UserService userService;

    private final DriverLicenceService driverLicenceService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final RentHistoryService rentHistoryService;

    @Autowired
    public CarService(CarRepository carRepository, UserService userService, RentHistoryService rentHistoryService, DriverLicenceService driverLicenceService) {
        this.carRepository = carRepository;
        this.userService = userService;
        this.rentHistoryService = rentHistoryService;
        this.driverLicenceService = driverLicenceService;
    }

    public Car getCarById(int id) {
        try {
            Car car = carRepository.findById(id).get();
            if(car == null) {
                throw new NoSuchElementException();
            } else {
                return car;
            }
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        return new Car();
    }

    public ArrayList<Car> getAllCar() {
        return (ArrayList<Car>) carRepository.findAll();
    }

    public ArrayList<Car> getFreeCar() {
        return carRepository.getCars();
    }

    public void createCar(Car car) {
        carRepository.save(car);
    }

    public void updateCar(Car car) {
        carRepository.saveAndFlush(car);
    }

    public void deleteCar(int id) {
        carRepository.deleteById(id);
    }

    public void rentCar(Integer id) {
        try {
            User user = userService.getUserByLogin();
            Car car = getCarById(id);
            DriverLicence driverLicence = driverLicenceService.getDriverLIcencesByUserID(user.getId());
            LocalDate localDate = LocalDate.now();
            if(driverLicence != null && !localDate.isAfter(driverLicence.getIssued())) {
                if (user.getRentHistoryStartTime() == null) {
                    if(car.isBooking()) {
                        throw new RentException("car is booking");
                    }
                    carRepository.bookCar(id);
                    rentHistoryService.WriteRentHistoryStartTime(user, car);
                } else {
                    throw new RentException("you have already rented a car");
                }
            } else {
                throw new RentException("You don't have a driver's license or is it expired");
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
