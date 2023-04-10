package rentcar.rentcar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import rentcar.rentcar.domain.DriverLicence;
import rentcar.rentcar.domain.Image;
import rentcar.rentcar.repository.ImageRepository;

import java.io.IOException;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    private final UserService userService;
    private final DriverLicenceService driverLicenceService;

    @Autowired
    public ImageService(ImageRepository imageRepository, UserService userService, DriverLicenceService driverLicenceService) {
        this.imageRepository = imageRepository;
        this.userService = userService;
        this.driverLicenceService = driverLicenceService;
    }

    @Transactional
    public void savePhoto(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setData(file.getBytes());
        DriverLicence driverLicence = driverLicenceService.getDriverLicencesByUserID(userService.getUserByLogin().getId());
        image.setDriverLicenceId(driverLicence.getId());
        imageRepository.save(image);
    }

}
