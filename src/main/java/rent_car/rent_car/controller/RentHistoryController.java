package rent_car.rent_car.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import rent_car.rent_car.domain.RentHistory;
import rent_car.rent_car.service.RentHistoryService;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/rh", produces = MediaType.APPLICATION_JSON_VALUE)
public class RentHistoryController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final RentHistoryService rentHistoryService;

    @Autowired
    public RentHistoryController(RentHistoryService rentHistoryService) {
        this.rentHistoryService = rentHistoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentHistory> getRentHistoryById(@PathVariable int id) {
        RentHistory rentHistory = rentHistoryService.getRentHistoryById(id);
        return new ResponseEntity<>(rentHistory, rentHistory.getId() != 0 ? HttpStatus.OK : HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<ArrayList<RentHistory>> getAllRentHistory() {
        return new ResponseEntity<>(rentHistoryService.getAllRentHistory(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createRentHistory(@RequestBody RentHistory rentHistory, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        rentHistoryService.createRentHistory(rentHistory);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updareRentHistory(@RequestBody RentHistory rentHistory, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        rentHistoryService.updateRentHistory(rentHistory);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteRentHistory(@RequestBody RentHistory rentHistory) {
        rentHistoryService.deleteRentHistory(rentHistory);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
