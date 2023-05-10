package rentcar.rentcar.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rentcar.rentcar.domain.User;
import rentcar.rentcar.repository.UserRepository;
import rentcar.rentcar.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Mock
    private UserService userService;

    private User user;

    private List<User> users;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUser() {
        user = new User(24, "ivan", "fadeev", "+375336291282", "ivanfadeev2003@mail.ru", "kaka", "kaka", "ADMIN", null);
        users = new ArrayList<>();
        users.add(user);
        userRepository.save(user);
    }

    @WithMockUser(username = "user", password = "user", roles = "ADMIN")
    @Test
    void getUserById() throws Exception {
        when(userService.getUserById(user.getId())).thenReturn(user);
        this.mockMvc.perform(get("/user/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId())));
    }

    @WithMockUser(username = "user", password = "user", roles = "ADMIN")
    @Test
    void getAllUser() throws Exception {
        when(userService.getAllUser()).thenReturn((ArrayList<User>) users);
        this.mockMvc.perform(get("/user/all", users))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id", is(user.getId())));
    }

}