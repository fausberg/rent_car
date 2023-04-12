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
import rentcar.rentcar.domain.DriverLicence;
import rentcar.rentcar.service.DriverLicenceService;

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
        if (driverLicenceService.getAllDriverLicence() != null) {
            return new ResponseEntity<>(driverLicenceService.getAllDriverLicence(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createDriverLicence(@RequestBody DriverLicence driverLicence) {
        if (driverLicenceService.createDriverLicence(driverLicence)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateDriverLicence(@RequestBody DriverLicence driverLicence) {
        if (driverLicenceService.updateDriverLicence(driverLicence)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<HttpStatus> updateDriverLicenceStatus(@PathVariable int id) {
        if (driverLicenceService.updateDriverLicenceStatus(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update/admin")
    public ResponseEntity<HttpStatus> updateAdminDriverLicence(@RequestBody DriverLicence driverLicence) {
        if (driverLicenceService.updateAdminDriverLicence(driverLicence)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAdminDriverLicence(@PathVariable int id) {
        if (driverLicenceService.deleteAdminDriverLicence(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
