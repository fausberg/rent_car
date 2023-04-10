package rentcar.rentcar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rentcar.rentcar.domain.CreditCard;
import rentcar.rentcar.domain.request.RegistrationCreditCard;
import rentcar.rentcar.service.CreditCardService;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/credit-card", produces = MediaType.APPLICATION_JSON_VALUE)
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
        return new ResponseEntity<>(creditCard, creditCard.getId() != 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<ArrayList<CreditCard>> getAllCreditCards() {
        return new ResponseEntity<>(creditCardService.getAllCreditCard(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateCreditCard(@RequestBody @Valid CreditCard creditCard, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for(ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(creditCardService.updateCreditCard(creditCard)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCreditCardById(@PathVariable int id) {
        try {
            if(creditCardService.deleteCreditCard(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
