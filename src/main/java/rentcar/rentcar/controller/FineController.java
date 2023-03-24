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
import rentcar.rentcar.domain.Fine;
import rentcar.rentcar.service.FineService;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/fine", produces = MediaType.APPLICATION_JSON_VALUE)
public class FineController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final FineService fineService;

    @Autowired
    public FineController(FineService fineService) {
        this.fineService = fineService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fine> getFineById(@PathVariable int id) {
        Fine fine = fineService.getFineById(id);
        return new ResponseEntity<>(fine, fine.getId() != 0 ? HttpStatus.OK : HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<ArrayList<Fine>> getAllFines() {
        return new ResponseEntity<>(fineService.getAllFine(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createFine(@RequestBody Fine fine, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for(ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        fineService.createFine(fine);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateFine(@RequestBody Fine fine, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for(ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        fineService.updateFine(fine);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteFine(@RequestBody Fine fine) {
        fineService.deleteFine(fine);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
