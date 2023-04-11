package rentcar.rentcar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.Car;
import rentcar.rentcar.domain.DTO.CarDTO;
import rentcar.rentcar.domain.DriverLicence;
import rentcar.rentcar.domain.User;
import rentcar.rentcar.exception.RentException;
import rentcar.rentcar.repository.CarRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
            if (car == null) {
                throw new NoSuchElementException();
            } else {
                return car;
            }
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        return new Car();
    }

    public List<Car> getAllCar() {
        return carRepository.findAll();
    }

    public ArrayList<Car> getFreeCar() {
        return carRepository.getCars();
    }

    public void createCar(CarDTO carDTO) {
        Car car = new Car(1, carDTO.getNumber(), carDTO.getColor(), carDTO.getModel(), carDTO.getManufacturer(), carDTO.getPrice(), false);
        carRepository.save(car);
    }

    public boolean updateCar(CarDTO carDTO) {
        ArrayList<Car> cars = (ArrayList<Car>) getAllCar();
        for (Car carOfList : cars) {
            if(carOfList.getNumber().equals(carDTO.getNumber())) {
                Car car = new Car(carRepository.getCarByNumber(carDTO.getNumber()).getId(), carDTO.getNumber(), carDTO.getColor(), carDTO.getModel(), carDTO.getManufacturer(), carDTO.getPrice(), carDTO.isBooking());
                carRepository.saveAndFlush(car);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCarByNumber(String number) {
        if (carRepository.getCarByNumber(number) != null) {
            carRepository.deleteCarByNumber(number);
            return true;
        }
        return false;
    }

    public boolean rentCar(Integer id) {
        try {
            ArrayList<Car> cars = getFreeCar();
            for (Car carOfList : cars) {
                if (carOfList.getId() == id) {
                    User user = userService.getUserByLogin();
                    Car car = getCarById(id);
                    DriverLicence driverLicence = driverLicenceService.getDriverLicencesByUserID(user.getId());
                    LocalDate localDate = LocalDate.now();
                    if (driverLicence != null && !localDate.isAfter(driverLicence.getIssued()) && driverLicence.getStatus()) {
                        if (user.getRentHistoryStartTime() == null) {
                            if (car.isBooking()) {
                                throw new RentException("car is booking");
                            }
                            carRepository.bookCar(id);
                            rentHistoryService.writeRentHistoryStartTime(user, car);
                            return true;
                        } else {
                            throw new RentException("you have already rented a car");
                        }
                    } else {
                        throw new RentException("You don't have a driver's license or is it expired or is not checked");
                    }
                }
            }
            return false;
        } catch (RentException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean removeRentCar() {
        try {
            User user = userService.getUserByLogin();
            if (user.getRentHistoryStartTime() != null) {
                carRepository.removeTheBookingFromTheCar(rentHistoryService.writeRentHistoryEndTime(user).getCarId());
                return true;
            } else {
                throw new RentException("you don't have a rental car");
            }
        } catch (RentException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
