package rentcar.rentcar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import rentcar.rentcar.domain.Car;
import rentcar.rentcar.dto.CarDTO;
import rentcar.rentcar.service.CarService;

import javax.validation.Valid;
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
        return new ResponseEntity<>((ArrayList<Car>) carService.getAllCar(), HttpStatus.OK);
    }

    @GetMapping("/free")
    public ResponseEntity<ArrayList<Car>> getFreeCars() {
        return new ResponseEntity<>(carService.getFreeCar(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createCar(@RequestBody @Valid CarDTO car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        carService.createCar(car);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateCar(@RequestBody @Valid CarDTO car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        carService.updateCar(car);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCarById(@PathVariable int id) {
        try {
            if(carService.deleteCarById(id)) {
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
