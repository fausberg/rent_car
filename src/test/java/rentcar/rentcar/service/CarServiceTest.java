package rentcar.rentcar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rentcar.rentcar.domain.Car;
import rentcar.rentcar.domain.DTO.CarDTO;
import rentcar.rentcar.repository.CarRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserService userService;

    @Mock
    private DriverLicenceService driverLicenceService;

    @Mock
    private RentHistoryService rentHistoryService;

    private CarService carService;

    private Car car;

    private List<Car> cars;

    @BeforeEach
    void setCar() {
        MockitoAnnotations.openMocks(this);
        carService = new CarService(carRepository, userService, rentHistoryService, driverLicenceService);
        car = new Car(51, "1234", "blue", "m4", "bmw", 323, false);
        cars = new ArrayList<>();
        cars.add(car);
        carRepository.save(car);
    }


    @Test
    void getCarById() {
        when(carRepository.findById(car.getId())).thenReturn(Optional.ofNullable(car));
        Optional<Car> optionalArticle = Optional.ofNullable(carService.getCarById(car.getId()));

        assertTrue(optionalArticle.isPresent());
        verify(carRepository, times(1)).findById(car.getId());
    }

    @Test
    void getAllCar() {
        when(carRepository.findAll()).thenReturn(cars);
        Optional<List<Car>> optionalBooks = Optional.ofNullable(carService.getAllCar());

        assertTrue(optionalBooks.isPresent());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void getFreeCar() {
        when(carRepository.getCars()).thenReturn((ArrayList<Car>) cars);
        Optional<List<Car>> optionalBooks = Optional.ofNullable(carService.getFreeCar());

        assertTrue(optionalBooks.isPresent());
        verify(carRepository, times(1)).getCars();
    }

    @Test
    void createCar() {
        CarDTO newCar = new CarDTO("1234", "blue", "m4", "bmw", 323, false);
        carService.createCar(newCar);
        verify(carRepository, times(2)).save(ArgumentMatchers.any(Car.class));
    }

    @Test
    void deleteCarByNumber() {
        when(carRepository.getCarByNumber(car.getNumber())).thenReturn(car);
        assertTrue(carService.deleteCarByNumber(car.getNumber()));
        verify(carRepository, times(1)).deleteById(car.getId());
    }

}