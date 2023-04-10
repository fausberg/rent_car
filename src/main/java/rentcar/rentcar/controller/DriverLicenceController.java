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
import rentcar.rentcar.domain.DriverLicence;
import rentcar.rentcar.dto.DriverLicenceDTO;
import rentcar.rentcar.service.DriverLicenceService;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/driver-licence", produces = MediaType.APPLICATION_JSON_VALUE)
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
        return new ResponseEntity<>(driverLicence, driverLicence.getId() != 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<ArrayList<DriverLicence>> getAllDriverLicence() {
        return new ResponseEntity<>(driverLicenceService.getAllDriverLicence(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createDriverLicence(@RequestBody @Valid DriverLicenceDTO driverLicence, BindingResult bindingResult) {
        if(driverLicenceService.createDriverLicence(driverLicence)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateDriverLicence(@RequestBody @Valid DriverLicenceDTO driverLicence, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        driverLicenceService.updateDriverLicence(driverLicence);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteDriverLicence() {
        try {
            driverLicenceService.deleteDriverLicence();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
