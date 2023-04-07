package rentcar.rentcar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.DriverLicence;
import rentcar.rentcar.domain.User;
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

    public void createDriverLicence(DriverLicence driverLicence) {
        driverLicence.setUserId(userService.getUserByLogin().getId());
        driverLicenceRepository.save(driverLicence);
    }

    public void updateDriverLicence(DriverLicence driverLicence) {
        driverLicenceRepository.saveAndFlush(driverLicence);
    }

    public void deleteDriverLicence() {
        User user = userService.getUserByLogin();
        driverLicenceRepository.deleteDriverLicenceByUserId(user.getId());
    }

    public DriverLicence getDriverLIcencesByUserID(int id) {
        return driverLicenceRepository.getDriverLicenceByUserID(id);
    }
}
