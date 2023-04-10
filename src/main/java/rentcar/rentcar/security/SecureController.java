package rentcar.rentcar.security;

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
import rentcar.rentcar.domain.request.RegistrationCreditCard;
import rentcar.rentcar.domain.request.RegistrationUser;

import javax.validation.Valid;
import java.net.http.HttpResponse;

@RestController
@ControllerAdvice
@RequestMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
public class SecureController {
    private final SecureService secureService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SecureController(SecureService secureService) {
        this.secureService = secureService;
    }


    @PostMapping("/user")
    public ResponseEntity<HttpResponse> registrationUser(@RequestBody @Valid RegistrationUser registrationUser, BindingResult bindingResult) {
        try {
            if(bindingResult.hasErrors()) {
            for(ObjectError o : bindingResult.getAllErrors()) {
                log.warn("validation failed" + bindingResult.getFieldError());
            }
            throw new IllegalArgumentException();
        }
            secureService.registrationUser(registrationUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/credit-card")
    public ResponseEntity<HttpResponse> registrationUser(@RequestBody @Valid RegistrationCreditCard creditCard, BindingResult bindingResult) {if(bindingResult.hasErrors()) {
        for(ObjectError o : bindingResult.getAllErrors()) {
            log.warn(o.getDefaultMessage());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
        secureService.createCreditCard(creditCard);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
