package rent_car.rent_car.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rent_car.rent_car.domain.Car;
import rent_car.rent_car.repository.CarRepository;

import java.util.ArrayList;

@Service
public class CarService {

    public CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car getCarById(int id) {
        return carRepository.findById(id).get();
    }

    public ArrayList<Car> getAllCar() {
        return (ArrayList<Car>) carRepository.findAll();
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
}
