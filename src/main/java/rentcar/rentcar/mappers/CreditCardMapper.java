package rentcar.rentcar.mappers;

import org.springframework.stereotype.Component;
import rentcar.rentcar.domain.CreditCard;
import rentcar.rentcar.domain.DTO.CreditCardDTO;
import rentcar.rentcar.service.UserService;

@Component
public class CreditCardMapper {

    private UserService userService;

    public CreditCard mapCreditCardDTOtoCreditCard(CreditCardDTO creditCardDTO) {
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber(creditCardDTO.getCardNumber());
        creditCard.setDate(creditCardDTO.getDate());
        creditCard.setCVV(creditCardDTO.getCVV());
        creditCard.setUserId(userService.getUserByLogin().getId());
        return creditCard;
    }

    public CreditCardDTO mapCreditCardToCreditCardDTO(CreditCard creditCard) {
        CreditCardDTO creditCardDTO = new CreditCardDTO();
        creditCardDTO.setCardNumber(creditCard.getCardNumber());
        creditCardDTO.setDate(creditCard.getDate());
        creditCardDTO.setCVV(creditCard.getCVV());
        return creditCardDTO;
    }
}
