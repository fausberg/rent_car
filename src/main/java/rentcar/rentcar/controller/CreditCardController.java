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
import rentcar.rentcar.domain.DTO.CreditCardDTO;
import rentcar.rentcar.mappers.CreditCardMapper;
import rentcar.rentcar.service.CreditCardService;

import javax.validation.Valid;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/credit-card", produces = MediaType.APPLICATION_JSON_VALUE)
public class CreditCardController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final CreditCardService creditCardService;

    private final CreditCardMapper creditCardMapper;

    @Autowired
    public CreditCardController(CreditCardService creditCardService, CreditCardMapper creditCardMapper) {
        this.creditCardService = creditCardService;
        this.creditCardMapper = creditCardMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCardDTO> getCreditCardById(@PathVariable int id) {
        CreditCard creditCard = creditCardService.getCreditCardById(id);
        CreditCardDTO creditCardDTO = creditCardMapper.mapCreditCardToCreditCardDTO(creditCard);
        return new ResponseEntity<>(creditCardDTO, creditCard.getId() != 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CreditCardDTO>> getAllCreditCards() {
        if (!creditCardService.getAllCreditCard().isEmpty()) {
            List<CreditCard> creditCards = creditCardService.getAllCreditCard();
            List<CreditCardDTO> creditCardDTOS = new ArrayList<>();
            for (CreditCard creditCard : creditCards) {
                creditCardDTOS.add(creditCardMapper.mapCreditCardToCreditCardDTO(creditCard));
            }
            return new ResponseEntity<>(creditCardDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateCreditCard(@RequestBody @Valid CreditCardDTO creditCardDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (creditCardService.updateCreditCard(creditCardMapper.mapCreditCardDTOtoCreditCard(creditCardDTO))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCreditCardById(@PathVariable int id) {
        try {
            if (creditCardService.deleteCreditCard(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/admin")
    public ResponseEntity<HttpStatus> updateAdminCreditCard(@RequestBody @Valid CreditCardDTO creditCardDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (creditCardService.updateAdminCreditCard(creditCardMapper.mapCreditCardDTOtoCreditCard(creditCardDTO))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAdminCreditCardById(@PathVariable int id) {
        try {
            if (creditCardService.deleteAdminCreditCard(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> registrationCreditCard(@RequestBody @Valid CreditCardDTO creditCardDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (creditCardService.createCreditCard(creditCardDTO)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
