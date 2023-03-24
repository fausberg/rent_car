package rentcar.rentcar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping()
    public ResponseEntity<HttpResponse> registrationUser(@RequestBody RegistrationUser registrationUser) {
        return new ResponseEntity<>(secureService.registration(registrationUser) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }
}
