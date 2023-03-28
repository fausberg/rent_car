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
        return new ResponseEntity<>(car, car.getId() != 0 ? HttpStatus.OK : HttpStatus.CONFLICT);
    }

    @GetMapping("all")
    public ResponseEntity<ArrayList<Car>> getAllCars() {
        return new ResponseEntity<>(carService.getAllCar(), HttpStatus.OK);
    }

    @GetMapping("free")
    public ResponseEntity<ArrayList<Car>> getFreeCars() {
        return new ResponseEntity<>(carService.getFreeCar(), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<HttpStatus> createCar(@RequestBody Car car, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for(ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        carService.createCar(car);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("update")
    public ResponseEntity<HttpStatus> updateCar(@RequestBody Car car, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        carService.updateCar(car);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<HttpStatus> deleteCar(@PathVariable int id) {
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("start/{id}")
    public ResponseEntity<HttpStatus> startBooking(@PathVariable Integer id) {
        carService.rentCar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("end")
    public ResponseEntity<HttpStatus> removeTheBookingFromTheCar() {
        carService.removeRentCar();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
