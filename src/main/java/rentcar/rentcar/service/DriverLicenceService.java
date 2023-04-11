package rentcar.rentcar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.DriverLicence;
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

    public boolean createDriverLicence(DriverLicence driverLicence) {
        try {
            System.out.println(driverLicence);
            DriverLicence newDriverLicence = null;
            newDriverLicence = getDriverLicencesByUserID(userService.getUserByLogin().getId());
            if (newDriverLicence == null) {
                System.out.println("Я тут");
                driverLicence.setStatus(false);
                driverLicence.setUserId(userService.getUserByLogin().getId());
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

    public boolean updateDriverLicence(DriverLicence driverLicence) {
        DriverLicence newDriverLicence = getDriverLicencesByUserID(userService.getUserByLogin().getId());
        if(driverLicence.getId() == newDriverLicence.getId()) {
            driverLicence.setUserId(userService.getUserByLogin().getId());
            driverLicenceRepository.saveAndFlush(driverLicence);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateDriverLicenceStatus(int id) {
        DriverLicence newDriverLicence = getDriverLicencesByUserID(userService.getUserByLogin().getId());
        if(id == newDriverLicence.getId()) {
            newDriverLicence.setStatus(true);
            driverLicenceRepository.saveAndFlush(newDriverLicence);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateAdminDriverLicence(DriverLicence driverLicence) {
        ArrayList<DriverLicence> driverLicences = getAllDriverLicence();
        for (DriverLicence newDriverLicence : driverLicences) {
            if (driverLicence.getId() == newDriverLicence.getId()) {
                driverLicence.setUserId(userService.getUserByLogin().getId());
                driverLicenceRepository.saveAndFlush(driverLicence);
                return true;
            }
        }
        return false;
    }

    public boolean deleteAdminDriverLicence(int id) {
        ArrayList<DriverLicence> driverLicences = getAllDriverLicence();
        for (DriverLicence driverLicence : driverLicences) {
            if(driverLicence.getId() == id) {
                driverLicenceRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

    public DriverLicence getDriverLicencesByUserID(int id) {
        return driverLicenceRepository.getDriverLicenceByUserID(id);
    }
}
