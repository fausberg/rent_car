package rentcar.rentcar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.DriverLicence;
import rentcar.rentcar.repository.DriverLicenceRepository;

import java.util.ArrayList;

@Service
public class DriverLicenceService {

    public DriverLicenceRepository driverLicenceRepository;

    @Autowired
    public DriverLicenceService(DriverLicenceRepository driverLicenceRepository) {
        this.driverLicenceRepository = driverLicenceRepository;
    }

    public DriverLicence getDriverLicenceById(int id) {
        return driverLicenceRepository.getDriverLicenceById(id);
    }

    public ArrayList<DriverLicence> getAllDriverLicence() {
        return driverLicenceRepository.getAllDriverLicence();
    }

    public void createDriverLicence(DriverLicence driverLicence) {
        driverLicenceRepository.createDriverLicence(driverLicence);
    }

    public void updateDriverLicence(DriverLicence driverLicence) {
        driverLicenceRepository.updateDriverLicence(driverLicence);
    }

    public void deleteDriverLicence(DriverLicence driverLicence) {
        driverLicenceRepository.deleteDriverLicence(driverLicence);
    }
}
