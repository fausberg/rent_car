package rentcar.rentcar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rentcar.rentcar.domain.DriverLicence;
import rentcar.rentcar.repository.DriverLicenceRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class DriverLicenceServiceTest {

    @Mock
    private DriverLicenceRepository driverLicenceRepository;

    private DriverLicenceService driverLicenceService;

    private UserService userService;

    private DriverLicence driverLicence;

    private List<DriverLicence> driverLicences;

    @BeforeEach
    void setDriverLicence() {
        MockitoAnnotations.openMocks(this);
        driverLicenceService = new DriverLicenceService(driverLicenceRepository, userService);
        driverLicence = new DriverLicence(11, LocalDate.now(), LocalDate.now(), 1, false);
        driverLicences = new ArrayList<>();
        driverLicences.add(driverLicence);
        driverLicenceRepository.save(driverLicence);
    }

    @Test
    void getDriverLicenceById() {
        when(driverLicenceRepository.findById(driverLicence.getId())).thenReturn(Optional.ofNullable(driverLicence));
        Optional<DriverLicence> optionalArticle = Optional.ofNullable(driverLicenceService.getDriverLicenceById(driverLicence.getId()));

        assertTrue(optionalArticle.isPresent());
        verify(driverLicenceRepository, times(1)).findById(driverLicence.getId());
    }

    @Test
    void getAllDriverLicence() {
        when(driverLicenceRepository.findAll()).thenReturn(driverLicences);
        Optional<List<DriverLicence>> optionalBooks = Optional.ofNullable(driverLicenceService.getAllDriverLicence());

        assertTrue(optionalBooks.isPresent());
        verify(driverLicenceRepository, times(1)).findAll();
    }

}