package rentcar.rentcar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import rentcar.rentcar.domain.CreditCard;
import rentcar.rentcar.service.CreditCardService;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/cr", produces = MediaType.APPLICATION_JSON_VALUE)
public class CreditCardController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final CreditCardService creditCardService;

    @Autowired
    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCard> getCreditCardById(@PathVariable int id) {
        CreditCard creditCard = creditCardService.getCreditCardById(id);
        return new ResponseEntity<>(creditCard, creditCard.getId() != 0 ? HttpStatus.OK : HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<ArrayList<CreditCard>> getAllCreditCards() {
        return new ResponseEntity<>(creditCardService.getAllCreditCard(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateFine(@RequestBody CreditCard creditCard, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for(ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        creditCardService.updateCreditCard(creditCard);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteFine(@RequestBody CreditCard creditCard) {
        creditCardService.deleteCreditCard(creditCard);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
