package rentcar.rentcar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.Car;
import rentcar.rentcar.repository.CarRepository;

import java.util.ArrayList;

@Service
public class CarService {

    public CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car getCarById(int id) {
        return carRepository.getCarById(id);
    }

    public ArrayList<Car> getAllCar() {
        return carRepository.getAllCar();
    }

    public void createCar(Car car) {
        carRepository.createCar(car);
    }

    public void updateCar(Car car) {
        carRepository.updateCar(car);
    }

    public void deleteCar(Car car) {
        carRepository.deleteCar(car);
    }
}
