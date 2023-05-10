package rentcar.rentcar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.User;
import rentcar.rentcar.domain.DTO.UserDTO;
import rentcar.rentcar.exception.LoginException;
import rentcar.rentcar.repository.CreditCardRepository;
import rentcar.rentcar.repository.UserRepository;
import rentcar.rentcar.service.UserService;

@Service
public class SecureService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final CreditCardRepository creditCardRepository;

    @Autowired
    public SecureService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService, CreditCardRepository creditCardRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.creditCardRepository = creditCardRepository;
    }

    public boolean registrationUser(UserDTO UserDTO) {
        try {
            if (userRepository.getUserByLogin(UserDTO.getLogin()) != null) {
                throw new LoginException("login already exists");
            }
            User user = new User();
            user.setLogin(UserDTO.getLogin());
            user.setPassword(passwordEncoder.encode(UserDTO.getPassword()));
            user.setFirstName(UserDTO.getFirstName());
            user.setLastName(UserDTO.getLastName());
            user.setEmail(UserDTO.getEmail());
            user.setPhoneNumber(UserDTO.getPhoneNumber());
            user.setRole("USER");
            User savedUser = userRepository.save(user);
            return true;
        } catch (LoginException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
