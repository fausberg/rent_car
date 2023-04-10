package rentcar.rentcar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.CreditCard;
import rentcar.rentcar.domain.Fine;
import rentcar.rentcar.domain.RentHistory;
import rentcar.rentcar.domain.User;
import rentcar.rentcar.domain.request.RegistrationUser;
import rentcar.rentcar.repository.CreditCardRepository;
import rentcar.rentcar.repository.FineRepository;
import rentcar.rentcar.repository.RentHistoryRepository;
import rentcar.rentcar.repository.UserRepository;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CreditCardRepository creditCardRepository;

    private final RentHistoryRepository rentHistoryRepository;

    private final PasswordEncoder passwordEncoder;


    private final FineRepository fineRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserService(UserRepository userRepository, CreditCardRepository creditCardRepository, RentHistoryRepository rentHistoryRepository, FineRepository fineRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.creditCardRepository = creditCardRepository;
        this.rentHistoryRepository = rentHistoryRepository;
        this.fineRepository = fineRepository;
        this.passwordEncoder = passwordEncoder;
    }




    public User getUserById(int id) {
        try {
            User user  = userRepository.findById(id).get();
            if(user == null) {
                throw new NoSuchElementException();
            } else {
                return user;
            }
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        return new User();
    }

    public ArrayList<User> getAllUser() {
        return (ArrayList<User>) userRepository.findAll();
    }

    public boolean updateUser(User user) {
        if(getUserByLogin().getId() == user.getId()) {
            userRepository.saveAndFlush(user);
            return true;
        } else {
            return false;
        }
    }

    public void updateUserTimeBooking(User user) {
        userRepository.saveAndFlush(user);
    }

    public boolean deleteUser() {
        userRepository.deleteById(getUserByLogin().getId());
        return true;
    }

    public User getUserByLogin() {
        return userRepository.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public ArrayList<CreditCard> getAllCreditCardsByUserId() {
        return creditCardRepository.getCreditCardsByUserId(getUserByLogin().getId());
    }
    public ArrayList<Fine> getAllFinesByUserId() {
        return fineRepository.getFinesByUserId(getUserByLogin().getId());
    }
    public ArrayList<RentHistory> getAllRentHistoryByUserId() {
        return rentHistoryRepository.getRentHistoriesByUserId(getUserByLogin().getId());
    }
    public ArrayList<String> getAllLogins() {
        return userRepository.logins();
    }

}
