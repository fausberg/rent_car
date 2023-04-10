package rentcar.rentcar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import rentcar.rentcar.domain.Car;
import rentcar.rentcar.domain.User;
import rentcar.rentcar.repository.CreditCardRepository;
import rentcar.rentcar.repository.FineRepository;
import rentcar.rentcar.repository.RentHistoryRepository;
import rentcar.rentcar.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    private CreditCardRepository creditCardRepository;

    private RentHistoryRepository rentHistoryRepository;

    private PasswordEncoder passwordEncoder;

    private FineRepository fineRepository;

    private User user;

    private List<User> users;

    @BeforeEach
    void setUser() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, creditCardRepository, rentHistoryRepository, fineRepository, passwordEncoder);
        user = new User(1, "ewqeqw", "eqweqw", "+375336291282", "ivanfadeev2003@mail.ru", "kinder", "kinder", "ADMIN", null);
        users = new ArrayList<>();
        users.add(user);
        userRepository.save(user);
    }

    @Test
    void getUserById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        Optional<User> optionalArticle = Optional.ofNullable(userService.getUserById(user.getId()));

        assertTrue(optionalArticle.isPresent());
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void getAllUser() {
        when(userRepository.findAll()).thenReturn(users);
        Optional<List<User>> optionalBooks = Optional.ofNullable(userService.getAllUser());

        assertTrue(optionalBooks.isPresent());
        verify(userRepository, times(1)).findAll();
    }
}