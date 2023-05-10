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
import rentcar.rentcar.domain.DTO.DriverLicenceDTO;
import rentcar.rentcar.domain.DriverLicence;
import rentcar.rentcar.mappers.DriverLicenceMapper;
import rentcar.rentcar.service.DriverLicenceService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/driver-licence", produces = MediaType.APPLICATION_JSON_VALUE)
public class DriverLicenceController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final DriverLicenceService driverLicenceService;

    private final DriverLicenceMapper driverLicenceMapper;

    @Autowired
    public DriverLicenceController(DriverLicenceService driverLicenceService, DriverLicenceMapper driverLicenceMapper) {
        this.driverLicenceService = driverLicenceService;
        this.driverLicenceMapper = driverLicenceMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverLicenceDTO> getDriverLicenceById(@PathVariable int id) {
        DriverLicence driverLicence = driverLicenceService.getDriverLicenceById(id);
        DriverLicenceDTO driverLicenceDTO = driverLicenceMapper.mapDriverLicenceToDriverLicenceDTO(driverLicence);
        return new ResponseEntity<>(driverLicenceDTO, driverLicence.getId() != 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DriverLicenceDTO>> getAllDriverLicence() {
        if (!driverLicenceService.getAllDriverLicence().isEmpty()) {
            List<DriverLicence> driverLicences = driverLicenceService.getAllDriverLicence();
            List<DriverLicenceDTO> driverLicenceDTOS = new ArrayList<>();
            for (DriverLicence driverLicence : driverLicences) {
                driverLicenceDTOS.add(driverLicenceMapper.mapDriverLicenceToDriverLicenceDTO(driverLicence));
            }
            return new ResponseEntity<>(driverLicenceDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createDriverLicence(@RequestBody DriverLicenceDTO driverLicenceDTO) {
        if (driverLicenceService.createDriverLicence(driverLicenceMapper.mapDriverLicenceDTOtoDriverLicence(driverLicenceDTO))) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateDriverLicence(@RequestBody DriverLicenceDTO driverLicenceDTO) {
        if (driverLicenceService.updateDriverLicence(driverLicenceMapper.mapDriverLicenceDTOtoDriverLicence(driverLicenceDTO))) {
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
    public ResponseEntity<HttpStatus> updateAdminDriverLicence(@RequestBody DriverLicenceDTO driverLicenceDTO) {
        if (driverLicenceService.updateAdminDriverLicence(driverLicenceMapper.mapDriverLicenceDTOtoDriverLicence(driverLicenceDTO))) {
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
