package rentcar.rentcar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rentcar.rentcar.domain.Car;
import rentcar.rentcar.domain.DTO.CarDTO;
import rentcar.rentcar.repository.CarRepository;
import rentcar.rentcar.service.CarService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @Mock
    private CarService carService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarController carController;

    private Car car;

    private List<Car> cars;

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void setCar() {
        car = new Car(37, "1234", "red", "e63", "mercedes", 321, false);
        cars = new ArrayList<>();
        cars.add(car);
    }

    @WithMockUser(username = "user", password = "user", roles = "ADMIN")
    @Test
    void getCarById() throws Exception {
        when(carService.getCarById(anyInt())).thenReturn(car);
        this.mockMvc.perform(get("/car/{id}", car.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(car.getId())));
    }

    @WithMockUser(username = "user", password = "user", roles = "ADMIN")
    @Test
    void getAllCars() throws Exception {
        when(carService.getAllCar()).thenReturn((ArrayList<Car>) cars);
        this.mockMvc.perform(get("/car/all", cars))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id", is(car.getId())));
    }

    @WithMockUser(username = "user", password = "user", roles = "ADMIN")
    @Test
    void getFreeCars() throws Exception {
        when(carService.getFreeCar()).thenReturn((ArrayList<Car>) cars);
        this.mockMvc.perform(get("/car/free", cars))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].booking").value(false));
    }

    @WithMockUser(username = "user", password = "user", roles = "ADMIN")
    @Test
    void createCar() throws Exception {
        CarDTO newCar = new CarDTO("2345","blue", "m3", "bmw", 321, false);
        doNothing().when(carService).createCar(newCar);

        mockMvc.perform(post("/car/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newCar)))
                .andExpect(status().isCreated());
    }
    @WithMockUser(username = "user", password = "user", roles = "ADMIN")
    @Test
    void updateCar() throws Exception {
        CarDTO newCar = new CarDTO( "2345","blue", "m3", "bmw", 321, false);
        doNothing().when(carService).createCar(newCar);
        mockMvc.perform(put("/car/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newCar)))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", password = "user", roles = "ADMIN")
    @Test
    void deleteCarByNumber() throws Exception {
        String number = "1234";
        when(carService.deleteCarByNumber(number)).thenReturn(true);
        mockMvc.perform(delete("/car/delete/{id}", number))
                .andExpect(status().isOk());
    }

}