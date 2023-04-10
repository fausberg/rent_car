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
import rentcar.rentcar.domain.Fine;
import rentcar.rentcar.domain.RentHistory;
import rentcar.rentcar.domain.request.RegistrationUser;
import rentcar.rentcar.service.UserService;
import rentcar.rentcar.domain.User;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, user.getId() != 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<ArrayList<User>> getAllUser() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for(ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(userService.updateUser(user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteUser() {
        userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/credit-cards")
    public ResponseEntity<ArrayList<CreditCard>> getAllUserCreditCards() {
        return new ResponseEntity<>(userService.getAllCreditCardsByUserId(), HttpStatus.OK);
    }

    @GetMapping("/fines")
    public ResponseEntity<ArrayList<Fine>> getAllUserFines() {
        return new ResponseEntity<>(userService.getAllFinesByUserId(), HttpStatus.OK);
    }

    @GetMapping("/rent-histories")
    public ResponseEntity<ArrayList<RentHistory>> getAllRentHistoryByUserId() {
        return new ResponseEntity<>(userService.getAllRentHistoryByUserId(), HttpStatus.OK);
    }
}
