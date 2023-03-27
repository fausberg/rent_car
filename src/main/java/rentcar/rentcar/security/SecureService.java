package rentcar.rentcar.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rentcar.rentcar.domain.CreditCard;
import rentcar.rentcar.domain.User;
import rentcar.rentcar.domain.request.RegistrationUser;
import rentcar.rentcar.exception.LoginException;
import rentcar.rentcar.repository.CreditCardRepository;
import rentcar.rentcar.repository.UserRepository;
import rentcar.rentcar.service.UserService;

import java.util.ArrayList;

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

    @Transactional(rollbackFor = Exception.class)
    public boolean registrationUser(RegistrationUser registrationUser) {
        try {
            ArrayList<String> logins = userService.getAllLogins();
            for (String login : logins) {
                if(login.equals(registrationUser.getLogin())) {
                    throw new LoginException("login already exists");
                }
            }
            User user = new User();
            user.setLogin(registrationUser.getLogin());
            user.setPassword(passwordEncoder.encode(registrationUser.getPassword()));
            user.setFirstName(registrationUser.getFirstName());
            user.setLastName(registrationUser.getLastName());
            user.setEmail(registrationUser.getEmail());
            user.setPhoneNumber(registrationUser.getPhoneNumber());

            User savedUser = userRepository.save(user);

            user.setRole("USER");

            return true;
        } catch (LoginException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean createCreditCard(CreditCard creditCard) {
        try {
            User user = userService.getUserByLogin();
            System.out.println(creditCard);
            creditCard.setDate(passwordEncoder.encode(creditCard.getDate()));
            creditCard.setCVV(passwordEncoder.encode(creditCard.getCVV()));
            creditCard.setUserId(user.getId());
            System.out.println(creditCard);
            creditCardRepository.save(creditCard);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
