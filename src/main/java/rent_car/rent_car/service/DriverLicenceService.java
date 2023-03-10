package rent_car.rent_car.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rent_car.rent_car.domain.DriverLicence;
import rent_car.rent_car.repository.DriverLicenceRepository;

import java.util.ArrayList;

@Service
public class DriverLicenceService {

    public DriverLicenceRepository driverLicenceRepository;

    @Autowired
    public DriverLicenceService(DriverLicenceRepository driverLicenceRepository) {
        this.driverLicenceRepository = driverLicenceRepository;
    }

    public DriverLicence getDriverLicenceById(int id) {
        return driverLicenceRepository.findById(id).get();
    }

    public ArrayList<DriverLicence> getAllDriverLicence() {
        return (ArrayList<DriverLicence>) driverLicenceRepository.findAll();
    }

    public void createDriverLicence(DriverLicence driverLicence) {
        driverLicenceRepository.save(driverLicence);
    }

    public void updateDriverLicence(DriverLicence driverLicence) {
        driverLicenceRepository.saveAndFlush(driverLicence);
    }

    public void deleteDriverLicence(DriverLicence driverLicence) {
        driverLicenceRepository.delete(driverLicence);
    }
}
