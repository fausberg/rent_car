package rentcar.rentcar.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.User;
import rentcar.rentcar.domain.request.RegistrationUser;
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

    public boolean registrationUser(RegistrationUser registrationUser) {
        try {
            if (userRepository.getUserByLogin(registrationUser.getLogin()) != null) {
                throw new LoginException("login already exists");
            }
            User user = new User();
            user.setLogin(registrationUser.getLogin());
            user.setPassword(passwordEncoder.encode(registrationUser.getPassword()));
            user.setFirstName(registrationUser.getFirstName());
            user.setLastName(registrationUser.getLastName());
            user.setEmail(registrationUser.getEmail());
            user.setPhoneNumber(registrationUser.getPhoneNumber());
            user.setRole("USER");
            User savedUser = userRepository.save(user);
            return true;
        } catch (LoginException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
