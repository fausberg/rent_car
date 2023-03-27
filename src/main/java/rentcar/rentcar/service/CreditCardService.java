package rentcar.rentcar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rentcar.rentcar.domain.CreditCard;
import rentcar.rentcar.repository.CreditCardRepository;

import java.util.ArrayList;

@Service
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;

    @Autowired
    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public CreditCard getCreditCardById(int id) {
        return creditCardRepository.findById(id).get();
    }

    public ArrayList<CreditCard> getAllCreditCard() {
        return (ArrayList<CreditCard>) creditCardRepository.findAll();
    }

    public void updateCreditCard(CreditCard creditCard) {
        creditCardRepository.saveAndFlush(creditCard);
    }

    public void deleteCreditCard(CreditCard creditCard) {
        creditCardRepository.delete(creditCard);
    }
}
