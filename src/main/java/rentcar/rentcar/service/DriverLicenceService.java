package rentcar.rentcar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.DriverLicence;
import rentcar.rentcar.domain.User;
import rentcar.rentcar.dto.DriverLicenceDTO;
import rentcar.rentcar.exception.DriverLicenceException;
import rentcar.rentcar.repository.DriverLicenceRepository;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class DriverLicenceService {

    private final UserService userService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final DriverLicenceRepository driverLicenceRepository;

    @Autowired
    public DriverLicenceService(DriverLicenceRepository driverLicenceRepository, UserService userService) {
        this.driverLicenceRepository = driverLicenceRepository;
        this.userService = userService;
    }

    public DriverLicence getDriverLicenceById(int id) {
        try {
            DriverLicence driverLicence = driverLicenceRepository.findById(id).get();
            if(driverLicence == null) {
                throw new NoSuchElementException();
            } else {
                return driverLicence;
            }
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        return new DriverLicence();
    }

    public ArrayList<DriverLicence> getAllDriverLicence() {
        return (ArrayList<DriverLicence>) driverLicenceRepository.findAll();
    }

    public boolean createDriverLicence(DriverLicenceDTO driverLicenceDTO) {
        try {
            DriverLicence testDriverLicence = null;
            testDriverLicence = getDriverLicenceById(userService.getUserByLogin().getId());
            if (testDriverLicence == null) {
                DriverLicence driverLicence = new DriverLicence();
                driverLicence.setUserId(userService.getUserByLogin().getId());
                driverLicence.setExpire(driverLicenceDTO.getExpire());
                driverLicence.setIssued(driverLicenceDTO.getIssued());
                driverLicenceRepository.save(driverLicence);
                return true;
            } else {
                throw new DriverLicenceException("driver licence is already exist");
            }
        } catch (DriverLicenceException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public void updateDriverLicence(DriverLicenceDTO driverLicenceDTO) {
        DriverLicence driverLicence = new DriverLicence();
        driverLicence.setExpire(driverLicenceDTO.getExpire());
        driverLicence.setIssued(driverLicenceDTO.getIssued());
        driverLicenceRepository.saveAndFlush(driverLicence);
    }

    public void deleteDriverLicence() {
        User user = userService.getUserByLogin();
        driverLicenceRepository.deleteDriverLicenceByUserId(user.getId());
    }

    public DriverLicence getDriverLicencesByUserID(int id) {
        return driverLicenceRepository.getDriverLicenceByUserID(id);
    }
}
