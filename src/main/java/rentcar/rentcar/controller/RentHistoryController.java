package rentcar.rentcar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rentcar.rentcar.domain.RentHistory;
import rentcar.rentcar.service.RentHistoryService;

import javax.validation.Valid;
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
    public ResponseEntity<HttpStatus> updateRentHistory(@RequestBody @Valid RentHistory rentHistory, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        rentHistoryService.updateRentHistory(rentHistory);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<HttpStatus> deleteRentHistory(@PathVariable int id) {
        rentHistoryService.deleteRentHistory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
