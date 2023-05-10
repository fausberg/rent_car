package rentcar.rentcar.mappers;

import org.springframework.stereotype.Component;
import rentcar.rentcar.domain.DTO.DriverLicenceDTO;
import rentcar.rentcar.domain.DriverLicence;
import rentcar.rentcar.service.UserService;

@Component
public class DriverLicenceMapper {

    private UserService userService;

    public DriverLicence mapDriverLicenceDTOtoDriverLicence(DriverLicenceDTO driverLicenceDTO) {
        DriverLicence driverLicence = new DriverLicence();
        driverLicence.setIssued(driverLicenceDTO.getIssued());
        driverLicence.setExpire(driverLicenceDTO.getExpire());
        driverLicence.setUserId(driverLicenceDTO.getUserId());
        driverLicence.setStatus(false);
        return driverLicence;
    }

    public DriverLicenceDTO mapDriverLicenceToDriverLicenceDTO(DriverLicence driverLicence) {
        DriverLicenceDTO driverLicenceDTO = new DriverLicenceDTO();
        driverLicenceDTO.setIssued(driverLicence.getIssued());
        driverLicenceDTO.setExpire(driverLicence.getExpire());
        driverLicenceDTO.setUserId(driverLicence.getUserId());
        return driverLicenceDTO;
    }
}
