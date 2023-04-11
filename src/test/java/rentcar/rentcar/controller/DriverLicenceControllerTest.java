package rentcar.rentcar.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rentcar.rentcar.domain.DriverLicence;
import rentcar.rentcar.repository.DriverLicenceRepository;
import rentcar.rentcar.service.DriverLicenceService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class DriverLicenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DriverLicenceController driverLicenceController;

    @Mock
    private DriverLicenceService driverLicenceService;

    private DriverLicence driverLicence;

    private List<DriverLicence> driverLicences;

    @Autowired
    private DriverLicenceRepository driverLicenceRepository;

    @BeforeEach
    void setDriverLicence() {
        driverLicence = new DriverLicence(11, LocalDate.now(), LocalDate.now(), 1, false);
        driverLicences = new ArrayList<>();
        driverLicences.add(driverLicence);
        driverLicenceRepository.save(driverLicence);
    }

    @WithMockUser(username = "user", password = "user", roles = "ADMIN")
    @Test
    void getDriverLicenceById() throws Exception {
        when(driverLicenceService.getDriverLicenceById(anyInt())).thenReturn(driverLicence);
        this.mockMvc.perform(get("/driver-licence/{id}", driverLicence.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(driverLicence.getId())));
    }

    @WithMockUser(username = "user", password = "user", roles = "ADMIN")
    @Test
    void getAllDriverLicence() throws Exception {
        when(driverLicenceService.getAllDriverLicence()).thenReturn((ArrayList<DriverLicence>) driverLicences);
        this.mockMvc.perform(get("/driver-licence/all", driverLicences))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id", is(driverLicence.getId())));
    }
}