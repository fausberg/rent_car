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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rentcar.rentcar.domain.DTO.RentHistoryDTO;
import rentcar.rentcar.domain.RentHistory;
import rentcar.rentcar.mappers.RentHistoryMapper;
import rentcar.rentcar.service.RentHistoryService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/rent-history", produces = MediaType.APPLICATION_JSON_VALUE)
public class RentHistoryController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final RentHistoryService rentHistoryService;

    private final RentHistoryMapper rentHistoryMapper;

    @Autowired
    public RentHistoryController(RentHistoryService rentHistoryService, RentHistoryMapper rentHistoryMapper) {
        this.rentHistoryService = rentHistoryService;
        this.rentHistoryMapper = rentHistoryMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentHistoryDTO> getRentHistoryById(@PathVariable int id) {
        RentHistory rentHistory = rentHistoryService.getRentHistoryById(id);
        RentHistoryDTO rentHistoryDTO = rentHistoryMapper.mapRentHistoryToRentHistoryDTO(rentHistory);
        return new ResponseEntity<>(rentHistoryDTO, rentHistory.getId() != 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RentHistoryDTO>> getAllRentHistory() {
        if (!rentHistoryService.getAllRentHistory().isEmpty()) {
            List<RentHistory> rentHistories = rentHistoryService.getAllRentHistory();
            List<RentHistoryDTO> rentHistoryDTOS = new ArrayList<>();
            for (RentHistory rentHistory : rentHistories) {
                rentHistoryDTOS.add(rentHistoryMapper.mapRentHistoryToRentHistoryDTO(rentHistory));
            }
            return new ResponseEntity<>(rentHistoryDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateRentHistory(@RequestBody RentHistoryDTO rentHistoryDTO) {
        if (rentHistoryService.updateRentHistory(rentHistoryMapper.mapRentHistoryDTOtoRentHistory(rentHistoryDTO))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRentHistory(@PathVariable int id) {
        if (rentHistoryService.deleteRentHistory(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
