package rentcar.rentcar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.CreditCard;
import rentcar.rentcar.domain.Fine;
import rentcar.rentcar.domain.User;
import rentcar.rentcar.repository.CreditCardRepository;
import rentcar.rentcar.repository.FineRepository;
import rentcar.rentcar.repository.UserRepository;

import java.util.ArrayList;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CreditCardRepository creditCardRepository;

    private final FineRepository fineRepository;

    @Autowired
    public UserService(UserRepository userRepository, CreditCardRepository creditCardRepository, FineRepository fineRepository) {
        this.userRepository = userRepository;
        this.creditCardRepository = creditCardRepository;
        this.fineRepository = fineRepository;
    }

    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    public ArrayList<User> getAllUser() {
        return (ArrayList<User>) userRepository.findAll();
    }

    public void updateUser(User user) {
        userRepository.saveAndFlush(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public User getUserByLogin() {
        User user = userRepository.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        return user;
    }

    public ArrayList<CreditCard> getAllCreditCards() {
        return creditCardRepository.getCreditCardsByUserId(getUserByLogin().getId());
    }
    public ArrayList<Fine> getAllFines() {
        return fineRepository.getFinesByUserId(getUserByLogin().getId());
    }
    public ArrayList<String> getAllLogins() {
        return userRepository.logins();
    }
}
