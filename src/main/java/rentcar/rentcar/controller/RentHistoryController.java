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
import rentcar.rentcar.domain.RentHistory;
import rentcar.rentcar.service.RentHistoryService;

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

    @GetMapping("all")
    public ResponseEntity<ArrayList<RentHistory>> getAllRentHistory() {
        return new ResponseEntity<>(rentHistoryService.getAllRentHistory(), HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<HttpStatus> updateRentHistory(@RequestBody RentHistory rentHistory, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        rentHistoryService.updateRentHistory(rentHistory);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("delete")
    public ResponseEntity<HttpStatus> deleteRentHistory(@RequestBody RentHistory rentHistory) {
        rentHistoryService.deleteRentHistory(rentHistory);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
