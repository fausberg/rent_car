package rentcar.rentcar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rentcar.rentcar.domain.Car;
import rentcar.rentcar.domain.DTO.CarDTO;
import rentcar.rentcar.service.CarService;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/car", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable int id) {
        Car car = carService.getCarById(id);
        return new ResponseEntity<>(car, car.getId() != 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<ArrayList<Car>> getAllCars() {
        if(carService.getAllCar() != null) {
            carService.getAllCar();
            return new ResponseEntity<>((ArrayList<Car>) carService.getAllCar(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/free")
    public ResponseEntity<ArrayList<Car>> getFreeCars() {
        if(carService.getFreeCar() != null) {
            carService.getFreeCar();
            return new ResponseEntity<>(carService.getFreeCar(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createCar(@RequestBody CarDTO carDTO) {
        carService.createCar(carDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateCar(@RequestBody CarDTO carDTO) {
        if(carService.updateCar(carDTO)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{number}")
    public ResponseEntity<HttpStatus> deleteCarById(@PathVariable String number) {
        try {
            if(carService.deleteCarById(number)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/start/{id}")
    public ResponseEntity<HttpStatus> startBooking(@PathVariable Integer id) {
        try {
            if(carService.rentCar(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/end")
    public ResponseEntity<HttpStatus> removeTheBookingFromTheCar() {
        if(carService.removeRentCar()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
