package rentcar.rentcar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rentcar.rentcar.domain.CreditCard;
import rentcar.rentcar.domain.request.RegistrationUser;

import java.net.http.HttpResponse;

@RestController
@RequestMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
public class SecureController {
    private final SecureService secureService;

    @Autowired
    public SecureController(SecureService secureService) {
        this.secureService = secureService;
    }

    @PostMapping("/user")
    public ResponseEntity<HttpResponse> registrationUser(@RequestBody RegistrationUser registrationUser) {
        return new ResponseEntity<>(secureService.registrationUser(registrationUser) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @PostMapping("/cr")
    public ResponseEntity<HttpResponse> registrationUser(@RequestBody CreditCard creditCard) {
        return new ResponseEntity<>(secureService.createCreditCard(creditCard) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }
}
