package rentcar.rentcar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rentcar.rentcar.domain.Car;
import rentcar.rentcar.domain.CreditCard;
import rentcar.rentcar.repository.CreditCardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditCardServiceTest {

    @Mock
    private CreditCardRepository creditCardRepository;

    private UserService userService;

    private CreditCardService creditCardService;

    private CreditCard creditCard;

    private List<CreditCard> creditCards;

    @BeforeEach
    void setCreditCard() {
        MockitoAnnotations.openMocks(this);
        creditCardService = new CreditCardService(creditCardRepository, userService);
        creditCard = new CreditCard(1, "1234123412341234", "02/26", "313", 27);
        creditCards = new ArrayList<>();
        creditCards.add(creditCard);
        creditCardRepository.save(creditCard);
    }

    @Test
    void getCreditCardById() {
        when(creditCardRepository.findById(creditCard.getId())).thenReturn(Optional.ofNullable(creditCard));
        Optional<CreditCard> optionalArticle = Optional.ofNullable(creditCardService.getCreditCardById(creditCard.getId()));

        assertTrue(optionalArticle.isPresent());
        verify(creditCardRepository, times(1)).findById(creditCard.getId());
    }

    @Test
    void getAllCreditCard() {
        when(creditCardRepository.findAll()).thenReturn(creditCards);
        Optional<List<CreditCard>> optionalBooks = Optional.ofNullable(creditCardService.getAllCreditCard());

        assertTrue(optionalBooks.isPresent());
        verify(creditCardRepository, times(1)).findAll();
    }

}