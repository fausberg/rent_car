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
import rentcar.rentcar.domain.DriverLicence;
import rentcar.rentcar.service.DriverLicenceService;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/dl", produces = MediaType.APPLICATION_JSON_VALUE)
public class DriverLicenceController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final DriverLicenceService driverLicenceService;

    @Autowired
    public DriverLicenceController(DriverLicenceService driverLicenceService) {
        this.driverLicenceService = driverLicenceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverLicence> getDriverLicenceById(@PathVariable int id) {
        DriverLicence driverLicence = driverLicenceService.getDriverLicenceById(id);
        return new ResponseEntity<>(driverLicence, driverLicence.getId() != 0 ? HttpStatus.OK : HttpStatus.CONFLICT);
    }

    @GetMapping("all")
    public ResponseEntity<ArrayList<DriverLicence>> getAllDriverLicence() {
        return new ResponseEntity<>(driverLicenceService.getAllDriverLicence(), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<HttpStatus> createDriverLicence(@RequestBody DriverLicence driverLicence, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        driverLicenceService.createDriverLicence(driverLicence);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("update")
    public ResponseEntity<HttpStatus> updateDriverLicence(@RequestBody DriverLicence driverLicence, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        driverLicenceService.updateDriverLicence(driverLicence);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("delete")
    public ResponseEntity<HttpStatus> deleteDriverLicence(@RequestBody DriverLicence driverLicence) {
        driverLicenceService.deleteDriverLicence(driverLicence);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
