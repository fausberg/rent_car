package rentcar.rentcar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.CreditCard;
import rentcar.rentcar.domain.User;
import rentcar.rentcar.exception.CardException;
import rentcar.rentcar.repository.CreditCardRepository;

import java.util.ArrayList;
import java.util.NoSuchElementException;

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

    public void updateCreditCard(CreditCard creditCard) {
        creditCardRepository.saveAndFlush(creditCard);
    }

    public void deleteCreditCard(int id) {
        try {
            User user = userService.getUserByLogin();
            ArrayList<CreditCard> creditCards = creditCardRepository.getCreditCardsByUserId(user.getId());
            boolean hasCard = false;
            for (CreditCard creditCard : creditCards) {
                if (creditCard.getId() == id) {
                    creditCardRepository.deleteById(creditCard.getId());
                    hasCard = true;
                }
            }
            if(!hasCard) {
                throw new CardException("You do not have such a card");
            }
        } catch (CardException e) {
            log.error(e.getMessage());
        }
    }
}
