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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rentcar.rentcar.domain.Fine;
import rentcar.rentcar.service.FineService;

import javax.validation.Valid;
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
        return new ResponseEntity<>(fine, fine.getId() != 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<ArrayList<Fine>> getAllFines() {
        if(fineService.getAllFine() != null) {
            return new ResponseEntity<>(fineService.getAllFine(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createFine(@RequestBody @Valid Fine fine, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        fineService.createFine(fine);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateFine(@RequestBody Fine fine) {
        if (fineService.updateFine(fine)) {
            fineService.updateFine(fine);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<HttpStatus> deleteFine(@PathVariable int id) {
        if (fineService.deleteFine(id)) {
            fineService.deleteFine(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
