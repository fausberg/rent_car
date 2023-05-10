package rentcar.rentcar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.CreditCard;
import rentcar.rentcar.domain.DTO.CreditCardDTO;
import rentcar.rentcar.domain.DTO.UserDTO;
import rentcar.rentcar.domain.User;
import rentcar.rentcar.exception.CardException;
import rentcar.rentcar.repository.CreditCardRepository;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    @Autowired
    public CreditCardService(CreditCardRepository creditCardRepository, UserService userService) {
        this.creditCardRepository = creditCardRepository;
        this.userService = userService;
    }

    public CreditCard getCreditCardById(int id) {
        try {
            CreditCard creditCard = creditCardRepository.findById(id).get();
            if(creditCard == null) {
                throw new NoSuchElementException();
            } else {
                return creditCard;
            }
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        return new CreditCard();
    }

    public ArrayList<CreditCard> getAllCreditCard() {
        return (ArrayList<CreditCard>) creditCardRepository.findAll();
    }

    public boolean updateCreditCard(CreditCard creditCard) {
        User user = userService.getUserByLogin();
        ArrayList<CreditCard> creditCards = creditCardRepository.getCreditCardsByUserId(user.getId());
        for (CreditCard creditCardOfList : creditCards) {
            if (Objects.equals(creditCardOfList.getCardNumber(), creditCard.getCardNumber())) {
                creditCard.setId(creditCardOfList.getId());
                creditCardRepository.saveAndFlush(creditCard);
                return true;
            }
        }
        log.error("У вас нет такой карты");
        return false;
    }

    public boolean updateAdminCreditCard(CreditCard creditCard) {

        ArrayList<CreditCard> creditCards = (ArrayList<CreditCard>) creditCardRepository.findAll();
        for (CreditCard creditCardOfList : creditCards) {
            if (Objects.equals(creditCardOfList.getCardNumber(), creditCard.getCardNumber())) {
                creditCardRepository.saveAndFlush(creditCard);
                return true;
            }
        }
        log.error("У вас нет такой карты");
        return false;
    }

    public boolean deleteCreditCard(int id) {
        try {
            User user = userService.getUserByLogin();
            ArrayList<CreditCard> creditCards = creditCardRepository.getCreditCardsByUserId(user.getId());
            for (CreditCard creditCard : creditCards) {
                if (creditCard.getId() == id) {
                    creditCardRepository.deleteById(creditCard.getId());
                    return true;
                }
            }
            throw new CardException("You do not have such a card");
        } catch (CardException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean deleteAdminCreditCard(int id) {
        try {
            ArrayList<CreditCard> creditCards = (ArrayList<CreditCard>) creditCardRepository.findAll();
            for (CreditCard creditCard : creditCards) {
                if (creditCard.getId() == id) {
                    creditCardRepository.deleteById(creditCard.getId());
                    return true;
                }
            }
            throw new CardException("You do not have such a card");
        } catch (CardException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean createCreditCard(CreditCardDTO registrationCreditCard) {
        try {
            ArrayList<String> cardNumbers = creditCardRepository.cardNumbers();
            for(String cardNumber : cardNumbers) {
                if(cardNumber.equals(registrationCreditCard.getCardNumber())) {
                    throw new CardException("credit card already exists");
                }
            }
            User user = userService.getUserByLogin();
            CreditCard creditCard = new CreditCard();
            creditCard.setCardNumber(registrationCreditCard.getCardNumber());
            creditCard.setDate(registrationCreditCard.getDate());
            creditCard.setCVV(registrationCreditCard.getCVV());
            creditCard.setUserId(user.getId());
            creditCardRepository.save(creditCard);
            return true;
        } catch (CardException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
