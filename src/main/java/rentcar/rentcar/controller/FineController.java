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
import rentcar.rentcar.domain.DTO.FineDTO;
import rentcar.rentcar.domain.Fine;
import rentcar.rentcar.mappers.FineMapper;
import rentcar.rentcar.service.FineService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/fine", produces = MediaType.APPLICATION_JSON_VALUE)
public class FineController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final FineService fineService;

    private final FineMapper fineMapper;

    @Autowired
    public FineController(FineService fineService, FineMapper fineMapper) {
        this.fineService = fineService;
        this.fineMapper = fineMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FineDTO> getFineById(@PathVariable int id) {
        Fine fine = fineService.getFineById(id);
        FineDTO fineDTO = fineMapper.mapFineToFineDTO(fine);
        return new ResponseEntity<>(fineDTO, fine.getId() != 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FineDTO>> getAllFines() {
        if (!fineService.getAllFine().isEmpty()) {
            List<Fine> fines = fineService.getAllFine();
            List<FineDTO> fineDTOS = new ArrayList<>();
            for (Fine fine : fines) {
                fineDTOS.add(fineMapper.mapFineToFineDTO(fine));
            }
            return new ResponseEntity<>(fineDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createFine(@RequestBody @Valid FineDTO fineDTO) {
        fineService.createFine(fineMapper.mapFineDTOtoFine(fineDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateFine(@RequestBody FineDTO fineDTO) {
        if (fineService.updateFine(fineMapper.mapFineDTOtoFine(fineDTO))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<HttpStatus> deleteFine(@PathVariable int id) {
        if (fineService.deleteFine(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
